package com.shionfujie.lexicor.syntax.usecase

import com.shionfujie.lexicor.core
import com.shionfujie.lexicor.core.domain.{Lexeme, Target}
import com.shionfujie.lexicor.syntax.domain.Cond
import com.shionfujie.lexicor.syntax.usecase.ParsingState._
import com.shionfujie.lexicor.syntax.usecase.SyntaxNode._

sealed private[syntax] trait ParsingState

private[syntax] object ParsingState {

  case class FoundSubject(subject: Lexeme) extends ParsingState

  case class FoundKeyword(subject: Lexeme, keywords: List[Lexeme]) extends ParsingState

  case class FoundPredicate(keywords: List[Lexeme], expecting: String) extends ParsingState

  case class Terminated(cond: Cond) extends ParsingState

  object Starting extends ParsingState

}

private[syntax] class ParsingStateExt(state: ParsingState) {

  def nextState(syntaxNode: SyntaxNode, lexeme: Lexeme): ParsingState =
    (state, syntaxNode) match {
      case (Starting, _: Subject) =>
        FoundSubject(lexeme.asInstanceOf[core.domain.Subject])
      case (FoundSubject(subject), _: NonDeterminingPredicate) =>
        FoundKeyword(subject, List(lexeme))
      case (_: FoundSubject, d: DeterminingPredicate) =>
        FoundPredicate(List(lexeme), d.expecting)
      case (FoundKeyword(subject, keywords), _: NonDeterminingPredicate) =>
        FoundKeyword(subject, lexeme +: keywords)
      case (FoundKeyword(_, keywords), d: DeterminingPredicate) =>
        FoundPredicate(lexeme +: keywords, d.expecting)
      case (FoundPredicate(keywords, _), d: DeterminingPredicate) =>
        FoundPredicate(lexeme +: keywords, d.expecting)
      case (_: FoundPredicate, t: Terminating) =>
        Terminated(Cond(t.`type`, lexeme.asInstanceOf[Target].value))
      case _ =>
        throw new IllegalStateException(
          s"Make sure the branches be exhaustive: (state, syntaxNode) = " +
            s"${(state, syntaxNode)}, lexeme = $lexeme")
    }

}
