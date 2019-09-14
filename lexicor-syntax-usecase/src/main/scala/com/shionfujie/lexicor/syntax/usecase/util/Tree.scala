package com.shionfujie.lexicor.syntax.usecase.util

private[util] case class Entry[K, V](key: K, value: V)

private[syntax] case class Tree[K, V] private[util] (subtrees: Set[(Entry[K, V], Tree[K, V])]) {

  def merge(that: Tree[K, V], f: (V, V) => V): Tree[K, V] =
    Tree(Tree.merge(this.subtrees, that.subtrees, f))

  def foldWhile[R](z: R)(p: (R, K) => Boolean, op: (R, V) => R): R =
    subtrees.find(subtree => p(z, subtree._1.key)) match {
      case None            => z
      case Some((e, tree)) => tree.foldWhile(op(z, e.value))(p, op)
    }

}

private[syntax] object Tree {

  private type Subtree[K, V] = (Entry[K, V], Tree[K, V])
  private type Subtrees[K, V] = Set[Subtree[K, V]]

  def empty[K, D]: Tree[K, D] = factory.empty

  def apply[K, D](pair: (K, D), pairs: (K, D)*): Tree[K, D] = factory(pair, pairs: _*)

  private def merge[K, V](thisSubtrees: Subtrees[K, V], thatSubtrees: Subtrees[K, V], f: (V, V) => V): Subtrees[K, V] = {
    /** Assuming that the keys of `subtree` and `subtree1` coincide, merge them into a new subtree recursively */
    def newSubtree(subtree: Subtree[K, V], subtree1: Subtree[K, V], f: (V, V) => V): Subtree[K, V] = {
      val (Entry(key, value), tree) = subtree
      val (Entry(_, value1), tree1) = subtree1
      val newTree = Tree(subtrees = merge(tree.subtrees, tree1.subtrees, f))
      (Entry(key, f(value, value1)), newTree)
    }

    thisSubtrees.foldLeft(thatSubtrees)((accSubTrees, subtree) => {
      accSubTrees.find(_._1.key == subtree._1.key) match {
        case None           => accSubTrees + subtree
        case Some(subtree1) => (accSubTrees - subtree1) + newSubtree(subtree, subtree1, f)
      }
    })
  }

  private object factory {

    private val emptyInstance: Tree[Any, Any] = new Tree(Set.empty)

    def empty[K, D]: Tree[K, D] = emptyInstance.asInstanceOf[Tree[K, D]]

    def apply[K, D](pair: (K, D), pairs: (K, D)*): Tree[K, D] = {
      val (key0, data0) +: pairs0 = (pair +: pairs).reverse
      pairs0.foldLeft(factory(key0, data0)) {
        case (tree, (key1, data1)) => factory(key1, data1, tree)
      }
    }

    private def apply[K, D](key: K, value: D, tree: Tree[K, D] = empty[K, D]): Tree[K, D] =
      new Tree(Set((Entry(key, value), tree)))

  }

}
