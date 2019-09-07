package com.shionfujie.lexicor.syntax.implementation.adapter

import com.shionfujie.lexicor.core.domain.{Lexeme => DomainLexeme}
import com.shionfujie.lexicor.core.grpc.{Lexeme => GrpcLexeme}
import com.shionfujie.lexicor.core.implementation.adapter.Deserializer
import com.shionfujie.lexicor.syntax.grpc.SyntaxParseRequest

class SyntaxParseRequestDeserializer(lexemeDeserializer: Deserializer[GrpcLexeme, DomainLexeme])
  extends Deserializer[SyntaxParseRequest, List[DomainLexeme]] {

  override def deserialize(request: SyntaxParseRequest): List[DomainLexeme] =
    request.lexemes.map(lexemeDeserializer.deserialize).toList

}
