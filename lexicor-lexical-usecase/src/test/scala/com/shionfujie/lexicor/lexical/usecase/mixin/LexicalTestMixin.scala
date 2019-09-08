package com.shionfujie.lexicor.lexical.usecase.mixin

import com.shionfujie.lexicor.lexical.usecase.lexicalParsers
import org.scalatest._

import scala.util.parsing.input.CharSequenceReader

trait LexicalTestMixin extends Matchers {

  import lexicalParsers._

  implicit protected def convertToParserExt[T](parser: Parser[T]): ParserExt[T] =
    new ParserExt(parser)

  implicit protected def convertToParserShouldFailToParseInput[T](
      parser: Parser[T]): ParserShouldFailToParseInput[T] = new ParserShouldFailToParseInput(parser)

  protected class ParserExt[T](parser: Parser[T]) {

    def apply(input: String): T = phrase(parser)(new CharSequenceReader(input)) match {
      case Success(lexemes, _) => lexemes
      case _                   => throw new IllegalInputException
    }

  }

  protected class ParserShouldFailToParseInput[T](parser: Parser[T]) {

    def shouldFailToParse(input: String): Assertion =
      an[IllegalInputException] should be thrownBy parser(input)

  }

  private class IllegalInputException extends Exception

}
