package com.shionfujie.lexicor.core.implementation.ext

import com.shionfujie.lexicor.core.grpc.{Lexeme => GrpcLexeme}
import com.shionfujie.lexicor.core.grpc.Lexeme.{Value => GrpcValue}

object KeywordExt {
  def unapply(lexeme: GrpcLexeme): Option[((Int, Int), Symbol)] = {
    lexeme.value match {
      case GrpcValue.Subject(GrpcLexeme.Subject(Some(pos), keyword)) =>
        Option((pos.start, pos.end), Symbol(keyword))
      case _ => None
    }
  }
}

object SubjectExt {
  def unapply(lexeme: GrpcLexeme): Option[((Int, Int), Symbol)] = lexeme.value match {
    case GrpcValue.Keyword(GrpcLexeme.Keyword(Some(pos), keyword)) =>
      Option((pos.start, pos.end), Symbol(keyword))
    case _ => None
  }
}

object TagLiteralExt {
  def unapply(lexeme: GrpcLexeme): Option[((Int, Int), Seq[String])] = lexeme.value match {
    case GrpcValue.TagLiteral(GrpcLexeme.TagLiteral(Some(pos), path)) =>
      Option((pos.start, pos.end), path)
    case _ => None
  }
}

object UnknownExt {
  def unapply(lexeme: GrpcLexeme): Option[((Int, Int))] = lexeme.value match {
    case GrpcValue.Unknown(GrpcLexeme.Unknown(Some(pos))) =>
      Option((pos.start, pos.end))
    case _ => None
  }
}

object test {
  def soFarSoGood(lexeme: GrpcLexeme) = lexeme match {
    case KeywordExt(pos, keyword) => ???
    case SubjectExt(pos, keyword) => ???
    case TagLiteralExt(pos, path) => ???
    case UnknownExt(pos) => ???
    case _ => ???
  }
}

