package com.shionfujie.lexicor.syntax.usecase

import com.shionfujie.lexicor.syntax.domain.SyntaxType

private[syntax] sealed trait SyntaxNode {

  val expectation: Expectation

}

private[syntax] object SyntaxNode {

  case class Subject(expectation: Expectation) extends SyntaxNode
  case class NonDeterminingPredicate(expectation: Expectation) extends SyntaxNode
  case class DeterminingPredicate(expectation: Expectation, expecting: String) extends SyntaxNode
  case class Terminating(expectation: Expectation, `type`: SyntaxType) extends SyntaxNode

}