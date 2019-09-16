package com.shionfujie.lexicor.syntax.usecase

import com.shionfujie.lexicor.core.domain._
import com.shionfujie.lexicor.syntax.domain.Error
import com.shionfujie.lexicor.syntax.usecase.ParsingState._

private[syntax] object Error {

  def apply(state: ParsingState, input: List[Lexeme]): Error = state match {
    case FoundSubject(subject)               => illegalPredicate(subject, input, List())
    case FoundKeyword(subject, keywords)     => illegalPredicate(subject, input, keywords)
    case FoundPredicate(keywords, expecting) => targetExpected(keywords, expecting, input)
    case _                                   => throw new IllegalStateException(s"(state, input) = ${(state, input)}")
  }

  private def illegalPredicate(lexeme: Lexeme, input: List[Lexeme], keywords: List[Lexeme]): Error = {
    val l = keywords ::: input
    if (l.isEmpty) new Error(lexeme.pos.overflow, "predicate expected")
    else new Error(l.head.pos bridgeTo l.last.pos, "illegal predicate")
  }

  private def targetExpected(keywords: List[Lexeme], expecting: String, input: List[Lexeme]): Error =
    if (input.isEmpty) {
      val last = keywords.last.pos
      new Error(last.overflow, s"$expecting expected")
    } else {
      val h = input.head
      new Error(h.pos, s"$expecting expected, but found ${h.repr}")
    }

  def subjectExpected(input: List[Lexeme]): Error = {
    val pos =
      input.headOption.getOrElse(throw new IllegalStateException("called when input is empty")).pos
    new Error(pos, "subject expected")
  }

}
