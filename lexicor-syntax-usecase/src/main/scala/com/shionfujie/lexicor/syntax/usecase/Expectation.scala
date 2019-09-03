package com.shionfujie.lexicor.syntax.usecase

import com.shionfujie.lexicor.core.domain
import com.shionfujie.lexicor.core.domain.{Lexeme, Representations, Symbols}
import com.shionfujie.lexicor.core.domain.Lexemes.TagLiteral

import scala.reflect.ClassTag

private[usecase] sealed trait Expectation {

  def apply(lexeme: Lexeme): Boolean

}

private trait TargetExpectation extends Expectation {

  val expecting: String

}

private[usecase] object Expectations {

  val Tag: Expectation = KeywordExpectation(Symbols.Tag)

  val Is: Expectation = KeywordExpectation(Symbols.Is)

  val In: Expectation = KeywordExpectation(Symbols.In)

  val TagLiteral: TargetExpectation = Literal[TagLiteral](Representations.TagLiteral)

  private case class KeywordExpectation(keyword: Symbol) extends Expectation {

    override def apply(l: Lexeme): Boolean = l match {
      case k: domain.Keyword => k.keyword == this.keyword
      case _ => false
    }

  }

  private def Literal[L: ClassTag](e: String): TargetExpectation = new TargetExpectation {

    override val expecting: String = e

    override def apply(v1: Lexeme): Boolean = v1 match {
      case _: L => true
      case _ => false
    }

  }

}