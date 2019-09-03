package com.shionfujie.lexicor.syntax.usecase

import com.shionfujie.lexicor.core.domain.{Lexeme, Subject}
import com.shionfujie.lexicor.syntax.domain.Result

/** Parses a list of [[Lexeme]]s into a list of [[Result]]s. */
final class SyntaxParser {

  private val syntaxTree = SyntaxTree()

  def apply(lexemes: List[Lexeme]): List[Result] =
    (for (input <- splitAtSubject(lexemes)) yield syntaxTree.parse(input)).reverse

  /**
    * Returns a partition of `lexemes` so that each of its components has a heading [[Subject]] and
    * trailing non-Subjects. The first components may not have the heading [[Subject]] if `lexemes`
    * begins with a non-Subject.
    */
  private def splitAtSubject(lexemes: List[Lexeme]): List[List[Lexeme]] =
    lexemes.foldLeft(Nil: List[List[Lexeme]]) {
      case (Nil, lexeme)          => List(List(lexeme))
      case (acc, lexeme: Subject) => List(lexeme) :: acc
      case (a :: as, lexeme)      => (lexeme :: a) :: as
    }.map(_.reverse)

}
