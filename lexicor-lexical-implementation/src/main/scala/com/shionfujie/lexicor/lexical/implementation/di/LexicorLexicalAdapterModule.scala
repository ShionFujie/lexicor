package com.shionfujie.lexicor.lexical.implementation.di

import com.shionfujie.lexicor.core.domain.{Lexeme => DomainLexeme}
import com.shionfujie.lexicor.core.implementation.adapter.{Deserializer, Serializer}
import com.shionfujie.lexicor.core.implementation.di.LexicorCoreAdapterModule
import com.shionfujie.lexicor.lexical.grpc.{LexicalParseReply, LexicalParseRequest}
import com.shionfujie.lexicor.lexical.implementation.adapter.{
  LexicalParseReplySerializer,
  LexicalParseRequestDeserializer
}

final class LexicorLexicalAdapterModule(coreAdapterModule: LexicorCoreAdapterModule) {
  import coreAdapterModule.lexemeSerializer

  lazy val requestDeserializer: Deserializer[LexicalParseRequest, String] =
    new LexicalParseRequestDeserializer

  lazy val responseSerializer: Serializer[List[DomainLexeme], LexicalParseReply] =
    new LexicalParseReplySerializer(lexemeSerializer)

}
