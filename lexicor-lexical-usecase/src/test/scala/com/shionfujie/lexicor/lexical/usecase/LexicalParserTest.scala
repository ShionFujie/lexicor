package com.shionfujie.lexicor.lexical.usecase

import com.shionfujie.lexicor.core.domain
import com.shionfujie.lexicor.core.domain.Lexemes._
import com.shionfujie.lexicor.core.domain.Symbols
import com.shionfujie.lexicor.lexical.usecase.mixin.LexicalTestMixin
import org.scalatest.FunSpec
import org.scalatest.prop.TableDrivenPropertyChecks.{forAll, Table}

class LexicalParserTest extends FunSpec with LexicalTestMixin {

  private val lexicalParser = new LexicalParser

  describe("A LexicalParser") {

    it("should parse 'tag is #[First/Second third]'") {
      lexicalParser("tag is #[First/Second third]") shouldBe
        List(Subject((0, 2), Symbols.Tag), Keyword((4, 5), Symbols.Is), TagLiteral((7, 27), List("First", "Second third")))
    }

    it("should require that tokens be separated") {
      lexicalParser("tagis#[First]") shouldBe
        List(Unknown((0, 12)))
    }

  }

  describe("Individual parsers in the implementation:") {

    describe("keywords") {
      import lexicalParser._

      val testCases =
        Table("keywords and their parsers",
          (Symbols.Tag, tag),
          (Symbols.Is, is),
          (Symbols.In, in)
        )

      it("should parse their names respectively") {
        forAll(testCases) { testCase =>
          val (keyword, parser) = testCase
          val lexeme = parser(keyword.name)

          lexeme shouldBe a [domain.Keyword]

          val keywordLexeme = lexeme.asInstanceOf[domain.Keyword]

          assert(keywordLexeme.keyword == keyword)
        }
      }

    }

    describe("tagLiteral") {
      import lexicalParser.tagLiteral

      it("should parse '#[First/Second third]'") {
        tagLiteral("#[First/Second third]") shouldBe
          TagLiteral((0, 20), List("First", "Second third"))
      }

      it("should fail to parse empty path") {
        tagLiteral shouldFailToParse "#[]"
      }

    }

    describe("unknown") {
      import lexicalParser.unknown
      import lexicalParsers.rep

      it("should parse any input but white spaces") {
        rep(unknown)("First Second Third") shouldBe List(Unknown((0, 4)), Unknown((6, 11)), Unknown((13, 17)))
      }

    }

  }

}