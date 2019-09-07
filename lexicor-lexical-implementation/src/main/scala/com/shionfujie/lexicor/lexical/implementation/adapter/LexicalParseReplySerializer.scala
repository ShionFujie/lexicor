package com.shionfujie.lexicor.lexical.implementation.adapter

import com.shionfujie.lexicor.core.domain.{Lexeme => DomainLexeme}
import com.shionfujie.lexicor.core.grpc.{Lexeme => GrpcLexeme}
import com.shionfujie.lexicor.core.implementation.adapter.Serializer
import com.shionfujie.lexicor.lexical.grpc.LexicalParseReply

class LexicalParseReplySerializer(lexemeSerializer: Serializer[DomainLexeme, GrpcLexeme])
  extends Serializer[List[DomainLexeme], LexicalParseReply] {

  override def serialize(lexemes: List[DomainLexeme]): LexicalParseReply =
    LexicalParseReply(lexemes.map(lexemeSerializer.serialize)),

}
