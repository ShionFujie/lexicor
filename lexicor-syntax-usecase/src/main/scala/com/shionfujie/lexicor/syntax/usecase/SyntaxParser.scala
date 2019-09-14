package com.shionfujie.lexicor.syntax.usecase

import com.shionfujie.lexicor.core.domain.{Lexeme, Subject}
import com.shionfujie.lexicor.syntax.domain.Result

import scala.collection.mutable.ListBuffer

/** Parses a list of [[Lexeme]]s into a list of [[Result]]s. */
final class SyntaxParser {

  private val syntaxTree = SyntaxTree()

  def apply(lexemes: List[Lexeme]): List[Result] =
    for (input <- splitAtSubject(lexemes)) yield syntaxTree.parse(input)

  /**
    * Returns a partition of `lexemes` so that each of its components has a heading [[Subject]] and
    * trailing non-Subjects. The first components may not have the heading [[Subject]] if `lexemes`
    * begins with a non-Subject.
    */
  private def splitAtSubject(lexemes: List[Lexeme]): List[List[Lexeme]] = {
    if (lexemes.isEmpty) return List()

    val result: ListBuffer[List[Lexeme]] = ListBuffer()
    val acc: ListBuffer[Lexeme] = ListBuffer()
    for (lexeme <- lexemes)
      (result.isEmpty && acc.isEmpty, lexeme) match {
        case (false, lexeme: Subject) => result += acc.toList; acc.clear(); acc += lexeme
        case _                        => acc += lexeme
      }

    (result += acc.toList).toList
  }

}
