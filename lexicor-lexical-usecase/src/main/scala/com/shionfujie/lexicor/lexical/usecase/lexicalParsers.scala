package com.shionfujie.lexicor.lexical.usecase

import com.shionfujie.lexicor.core.domain._

import scala.util.parsing.combinator.Parsers

/** Provides factory methods construct a parser that understands the language with the power of [[Parsers]]. */
private object lexicalParsers extends Parsers {

  type Elem = Char

  /** Behaves as the same as [[Parsers.accept]] except an [[Elem]] with a [[Pos]] being returned instead. */
  implicit def chr(e: Elem): Parser[Elem With Pos] =
    acceptIf2(_ == e)("'" + e + "' expected but " + _ + " found")

  /** Behaves as the same as [[Parsers.acceptIf]] except an [[Elem]] with a [[Pos]] being returned instead. */
  private def acceptIf2(p: Elem => Boolean)(err: Elem => String): Parser[Elem With Pos] = in =>
    if (in.atEnd) Failure("end of input", in)
    else if (p(in.first)) Success(((in.offset, in.offset), in.first), in.rest)
    else Failure(err(in.first), in)

}
