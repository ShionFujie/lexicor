package com.shionfujie.lexicor.syntax.usecase

import com.shionfujie.lexicor.core.domain.Lexemes._
import com.shionfujie.lexicor.core.domain.{Lexeme, Symbols}
import com.shionfujie.lexicor.syntax.domain.{Cond, Error, SyntaxType}
import org.scalatest._


class SyntaxParserTest extends FunSuite with Matchers {

  import testCases._

  private val syntaxParser = new SyntaxParser

  private object testCases {
    val `tag is #[Target]` = "tag is #[Target]"
    val `tag is in #[First/Second third]` = "tag is in #[First/Second third]"
    val `tag` = "tag"
    val `tag fly #[Target]` = "tag fly #[Target]"
  }

  /** Test cases each entry of which consists of a string sentence written in the language on the left hand side and its
    * lexeme form on the right hand side. */
  private val lexemesOf = Map(
    `tag is #[Target]` -> List(Subject((0, 2), Symbols.Tag), Keyword((4, 5), Symbols.Is), TagLiteral((7, 20), List("Target"))),
    `tag is in #[First/Second third]` -> List(Subject((22, 24), Symbols.Tag), Keyword((26, 27), Symbols.Is), Keyword((19, 21), Symbols.In), TagLiteral((33, 51), List("First", "Second third"))),
    `tag` -> List(Subject((0, 2), Symbols.Tag)),
    `tag fly #[Target]` -> List(Subject((0, 2), Symbols.Tag), Unknown((4, 7)), TagLiteral((9, 22), List("Target"))),
  )

  test("parse") {
    val lexemes = List(lexemesOf(`tag is #[Target]`), lexemesOf(`tag is in #[First/Second third]`)).flatten
    val expected = List(Right(Cond(SyntaxType.TagIs, "Target")), Right(Cond(SyntaxType.TagIsIn, "First/Second third")))

    syntaxParser(lexemes) shouldBe expected
  }

  test("empty input") {
    val lexemes = List()
    val expected = List()

    syntaxParser(lexemes) shouldBe expected
  }

  test("missing predicate") {
    lexemesOf(`tag`) shouldFailBecauseOf new Error(at = (3, 4), message = "predicate expected")
  }

  test("illegal predicate") {
    lexemesOf(`tag fly #[Target]`) shouldFailBecauseOf new Error(at = (4, 22), message = "illegal predicate")
  }

  private class LexemesShouldFailBecauseOfReason(syntaxParser: SyntaxParser, lexemes: List[Lexeme]) {

    def shouldFailBecauseOf(reason: Error): Assertion = {
      val expected = List(Left(reason))
      syntaxParser(lexemes) shouldBe expected
    }

  }

  private implicit def convertToLexemesShouldFailBecauseOfReason(lexemes: List[Lexeme]): LexemesShouldFailBecauseOfReason = new LexemesShouldFailBecauseOfReason(syntaxParser, lexemes)


}
