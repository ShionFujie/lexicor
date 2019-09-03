package com.shionfujie.lexicor.syntax.usecase.util

import org.scalatest.FunSuite

class TreeTest extends FunSuite {

  test("factory method") {
    val expected: Tree[String, Int] =
      new Tree(Set((Entry(key = "1", value = 1),
        new Tree(Set((Entry(key = "2", value = 2),
          new Tree(Set((Entry(key = "3", value = 3),
            Tree.empty)))))))))

    assert(Tree(("1", 1), ("2", 2), ("3", 3)) == expected)
  }

  test("merge") {
    val firstTree = Tree(("1", 1), ("11", 11), ("111", 111))
    val secondTree = Tree(("1", 1), ("12", 12), ("111", 111))
    val finalTree = Tree(("2", 2), ("21", 21))
    val strategyToMerge: (Int, Int) => Int = _ + _
    val value = firstTree.merge(secondTree, strategyToMerge).merge(finalTree, strategyToMerge)

    val expected: Tree[String, Int] =
      new Tree(Set(
        (Entry(key = "1", value = 2), new Tree(Set(
          (Entry(key = "11", value = 11), new Tree(Set(
            (Entry(key = "111", value = 111), Tree.empty)
          ))),
          (Entry(key = "12", value = 12), new Tree(Set(
            (Entry(key = "111", value = 111), Tree.empty)
          )))
        ))),
        (Entry(key = "2", value = 2), new Tree(Set(
          (Entry(key = "21", value = 21), Tree.empty)
        )))
      ))

    assert(value == expected)
  }

  test("foldWhile") {
    // The branches in the test case are disjoint, i.e. direct entries of a (sub)tree have
    // matually distinct keys, which is the case I primarily intended.
    val tree: Tree[Int, Int] = new Tree(Set(
      (Entry(key = 0, value = 1), new Tree(Set(
        (Entry(key = 0, value = 3), new Tree(Set(
          (Entry(key = 1, value = 6), Tree.empty)
        ))),
        (Entry(key = 1, value = 4), new Tree(Set(
          (Entry(key = 0, value = 7), Tree.empty)
        )))
      ))),
      (Entry(key = 1, value = 2), new Tree(Set(
        (Entry(key = 1, value = 5), Tree.empty)
      )))
    ))

    assert(tree.foldWhile(0)(p = (_, k) => k % 2 == 0, op = (acc, v) => acc + v) == 4)
  }

}
