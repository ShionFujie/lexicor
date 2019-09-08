package com.shionfujie.lexicor.syntax.usecase

import com.shionfujie.lexicor.syntax.domain.SyntaxType
import com.shionfujie.lexicor.syntax.usecase.Expectations._

private[usecase] object SyntaxConstruction extends Enumeration {

  val TagIs = Val(SyntaxType.TagIs, Tag, Vector(Is), TagLiteral)
  val TagIsIn = Val(SyntaxType.TagIsIn, Tag, Vector(Is, In), TagLiteral)

  def unapply(v: SyntaxConstruction.Val)
    : Option[(SyntaxType, Expectation, Vector[Expectation], TargetExpectation)] = {
    val Val(syntaxType, subject, keywords, target) = v
    Some((syntaxType, subject, keywords, target))
  }

  case class Val(
      syntaxType: SyntaxType,
      subject: Expectation,
      keywords: Vector[Expectation],
      target: TargetExpectation
  ) extends super.Val

}
