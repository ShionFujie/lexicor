package com.shionfujie.lexicor.syntax
import com.shionfujie.lexicor.core.domain.Lexeme
import com.shionfujie.lexicor.syntax.domain.Result
import com.shionfujie.lexicor.syntax.usecase.ParsingState.Starting
import com.shionfujie.lexicor.syntax.usecase.SyntaxNode._
import com.shionfujie.lexicor.syntax.usecase.util.Tree

package object usecase {

  private[syntax] type SyntaxConstruction = SyntaxConstruction.Value

  private[syntax] type SyntaxTree = Tree[Expectation, SyntaxNode]

  private[syntax] class SyntaxTreeExt(tree: SyntaxTree) {
    treeExt =>

    def parse(input: List[Lexeme]): Result = parse(Starting, input)

    private def parse(state0: ParsingState, input0: List[Lexeme]): Result = {
      val (finalState, remaining) = tree.foldWhile((state0, input0))(
        p = (acc, expectation) => {
          val (_, input1) = acc
          input1.nonEmpty && expectation(input1.head)
        },
        op = (acc, syntaxNode) => {
          val (currentState, lexeme :: remaining1) = acc
          (currentState.nextState(syntaxNode, lexeme), remaining1)
        }
      )

      Result(finalState, remaining)
    }

  }

  private[syntax] object SyntaxTree {

    def apply(): SyntaxTree =
      SyntaxConstruction.values
        .map(toSyntaxTree)
        .reduce(_.merge(_, (a, _) => a))

    private def toSyntaxTree(syntax: SyntaxConstruction): SyntaxTree = {
      val s +: ss = for (n <- toNodes(syntax)) yield (n.expectation, n)
      Tree(s, ss: _*)
    }

    private def toNodes(syntaxConstruction: SyntaxConstruction): Vector[SyntaxNode] = {
      val SyntaxConstruction(t, subject, keywords, target) = syntaxConstruction
      Subject(subject) +: (keywords.init.map(NonDeterminingPredicate) ++
        Vector(DeterminingPredicate(keywords.last, target.expecting), Terminating(target, t)))
    }

  }

  implicit private[syntax] def convertToSyntaxTreeExt(tree: SyntaxTree): SyntaxTreeExt =
    new SyntaxTreeExt(tree)

  implicit private[syntax] def convertToParsingStateExt(
      parsingState: ParsingState): ParsingStateExt = new ParsingStateExt(parsingState)

}
