package com.shionfujie.lexicor.lexical.usecase

import com.shionfujie.lexicor.core.domain._
import com.shionfujie.lexicor.core.domain.Lexemes._

import scala.util.parsing.input.CharSequenceReader

/** Parses a string input into a list of [[Lexeme]]s. */
final class LexicalParser {
  import lexicalParsers._

  def apply(s: String): List[Lexeme] = apply(new CharSequenceReader(s))

  private def apply(input: Input): List[Lexeme] = lexemes(input) match {
    case Success(result, rem) =>
      if (rem.atEnd) result
      else fallback(result, rem)
    case NoSuccess(message, rem) =>
      throw new IllegalStateException(
        s"Any string should be parsable. Something went wrong! Reason: $message, Input: $rem")
  }

  private def fallback(result: List[Lexeme], rem: Input): List[Lexeme] = unknown(rem) match {
    case Success(u, rem1) =>
      val last = result.last
      val resultSoFar =
        if (last.pos isAdjacentTo u.pos) Unknown(last.pos bridgeTo u.pos) +: result.init
        else u +: result
      resultSoFar ::: apply(rem1)
    case _ =>
      throw new IllegalStateException("parser 'unknown' should always succeeds")
  }

  private def lexemes = repsep(tag | is | in | tagLiteral, ' ')

  private[usecase] def tag = subject(Symbols.Tag)

  private[usecase] def is = keyword(Symbols.Is)

  private[usecase] def in = keyword(Symbols.In)

  private[usecase] def tagLiteral = "#" ~ parens('[', path, ']') ^^ {
    case (start, "#") ~ ((end, p)) => TagLiteral(start bridgeTo end, p)
  }

  private[usecase] def unknown: Parser[Lexeme] = """\S+""".r ^^ { case (pos, _) => Unknown(pos) }

  private def path = nonEmpty ~ rep('/' ~> nonEmpty) ^^ { case x ~ xs => x :: xs }

  private def nonEmpty = unzip("""[^\]/]+""".r)

}
