package com.shionfujie.lexicor.core.implementation.adapter

import com.shionfujie.lexicor.core.domain.{Lexeme => DomainLexeme, Pos => DomainPos}
import com.shionfujie.lexicor.core.domain.Lexemes.{
  Keyword => DomainKeyword,
  Subject => DomainSubject,
  TagLiteral => DomainTagLiteral,
  Unknown => DomainUnknown
}
import com.shionfujie.lexicor.core.grpc.{Lexeme => GrpcLexeme, Pos => GrpcPos}
import com.shionfujie.lexicor.core.grpc.Lexeme.{
  Keyword => GrpcKeyword,
  Subject => GrpcSubject,
  TagLiteral => GrpcTagLiteral,
  Unknown => GrpcUnknown,
  Value => GrpcValue
}

class LexemeAdapter(
    posAdapter: Serializer[DomainPos, GrpcPos] with Deserializer[GrpcPos, DomainPos]
) extends Serializer[DomainLexeme, GrpcLexeme]
    with Deserializer[GrpcLexeme, DomainLexeme] {

  override def serialize(lexeme: DomainLexeme): GrpcLexeme = lexeme match {
    case DomainSubject(pos, keyword) =>
      GrpcLexeme().withSubject(GrpcSubject(pos = serializePos(pos), keyword = keyword.name))
    case DomainKeyword(pos, keyword) =>
      GrpcLexeme().withKeyword(GrpcKeyword(pos = serializePos(pos), keyword = keyword.name))
    case DomainTagLiteral(pos, path) =>
      GrpcLexeme().withTagLiteral(GrpcTagLiteral(pos = serializePos(pos), path = path))
    case DomainUnknown(pos) =>
      GrpcLexeme().withUnknown(GrpcUnknown(pos = serializePos(pos)))
  }

  private def serializePos(pos: DomainPos) = Some(posAdapter.serialize(pos))

  override def deserialize(lexeme: GrpcLexeme): DomainLexeme = lexeme.value match {
    case GrpcValue.Subject(GrpcLexeme.Subject(Some(pos), keyword)) =>
      DomainSubject(deserializePos(pos), Symbol(keyword))
    case GrpcValue.Keyword(GrpcLexeme.Keyword(Some(pos), keyword)) =>
      DomainKeyword(deserializePos(pos), Symbol(keyword))
    case GrpcValue.TagLiteral(GrpcLexeme.TagLiteral(Some(pos), path)) =>
      DomainTagLiteral(deserializePos(pos), path.toList)
    case GrpcValue.Unknown(GrpcLexeme.Unknown(Some(pos))) =>
      DomainUnknown(deserializePos(pos))
    case GrpcValue.Empty =>
      throw new IllegalStateException(s"${GrpcValue.Empty} is not serializable")
  }

  private def deserializePos(pos: GrpcPos) = posAdapter.deserialize(pos)

}
