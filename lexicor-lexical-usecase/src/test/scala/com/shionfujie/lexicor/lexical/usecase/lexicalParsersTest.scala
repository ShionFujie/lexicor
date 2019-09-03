package com.shionfujie.lexicor.lexical.usecase

import com.shionfujie.lexicor.lexical.usecase.mixin.LexicalTestMixin
import org.scalatest.FunSuite

class lexicalParsersTest extends FunSuite with LexicalTestMixin {

  import lexicalParsers._

  test("parens") {
    val parser = "Id" ~ parens('(', unzip("#[0-9]+".r), ')')
    val first ~ second = parser("Id(#0123)")

    first shouldBe((0, 1), "Id")
    second shouldBe((2, 8), "#0123")
  }

}
