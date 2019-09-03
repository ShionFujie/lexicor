package com.shionfujie.lexicor.syntax.usecase

import com.shionfujie.lexicor.core.domain.Lexeme
import com.shionfujie.lexicor.syntax.domain
import com.shionfujie.lexicor.syntax.domain.{Cond, Result}
import com.shionfujie.lexicor.syntax.usecase.ParsingState.Terminated

private[syntax] object Result {

  def apply(finalState: ParsingState, remaining: List[Lexeme]): Result =
    finalState match {
      case Terminated(result) =>
        if (remaining.isEmpty) Result.successful(result)
        else Result.failed(Error.subjectExpected(remaining))
      case _ =>
        Result.failed(Error(finalState, remaining))
    }

  private def successful(cond: Cond) = Right(cond)

  private def failed(error: domain.Error) = Left(error)

}
