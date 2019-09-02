package com.shionfujie.lexicor.lexical.usecase

import com.shionfujie.lexicor.core.domain._

import scala.util.parsing.combinator.Parsers

/** Provides factory methods construct a parser that understands the language with the power of [[Parsers]]. */
private object lexicalParsers extends Parsers {

  type Elem = Char

  private val whiteSpace = """\s+""".r

  /** Behaves as the same as [[Parsers.accept]] except an [[Elem]] with a [[Pos]] being returned instead. */
  implicit def chr(e: Elem): Parser[Elem With Pos] =
    acceptIf2(_ == e)("'" + e + "' expected but " + _ + " found")

  /** A parser that matches a string and returns it to a [[String]] with [[Pos]]. */
  implicit def literal(s: String): Parser[String With Pos] = in => {
    val source = in.source
    val offset = in.offset
    val start = handleWhiteSpace(source, offset)
    var i = 0
    var j = start
    while (i < s.length && j < source.length && s.charAt(i) == source.charAt(j)) {
      i += 1
      j += 1
    }
    if (i == s.length)
      Success(((start, j - 1), source.subSequence(start, j).toString), in.drop(j - offset))
    else {
      val found =
        if (start == source.length()) "end of source"
        else "'" + source.charAt(start) + "'"
      Failure(
        "'" + s + "' expected but " + found + " found",
        in.drop(start - offset)
      )
    }
  }

  /** Behaves as the same as [[Parsers.acceptIf]] except an [[Elem]] with a [[Pos]] being returned instead. */
  private def acceptIf2(p: Elem => Boolean)(err: Elem => String): Parser[Elem With Pos] = in =>
    if (in.atEnd) Failure("end of input", in)
    else if (p(in.first)) Success(((in.offset, in.offset), in.first), in.rest)
    else Failure(err(in.first), in)

  /** Behaves as the same as [[scala.util.parsing.combinator.RegexParsers.handleWhiteSpace]] except that it always skips white spaces */
  private def handleWhiteSpace(source: java.lang.CharSequence, offset: Int): Int =
    whiteSpace.findPrefixMatchOf(new SubSequence(source, offset)) match {
      case Some(matched) => offset + matched.end
      case None => offset
    }

}
