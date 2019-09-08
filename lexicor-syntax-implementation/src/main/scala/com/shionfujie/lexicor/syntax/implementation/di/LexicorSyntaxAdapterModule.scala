package com.shionfujie.lexicor.syntax.implementation.di

import com.shionfujie.lexicor.core.domain.{Lexeme => DomainLexeme}
import com.shionfujie.lexicor.core.implementation.adapter.{Deserializer, Serializer}
import com.shionfujie.lexicor.core.implementation.di.LexicorCoreAdapterModule
import com.shionfujie.lexicor.syntax.domain.{Result => DomainResult}
import com.shionfujie.lexicor.syntax.grpc._
import com.shionfujie.lexicor.syntax.implementation.adapter._

final class LexicorSyntaxAdapterModule(
    coreAdapterModule: LexicorCoreAdapterModule
) {
  import coreAdapterModule._

  lazy val requestDeserializer: Deserializer[SyntaxParseRequest, List[DomainLexeme]] =
    new SyntaxParseRequestDeserializer(lexemeDeserializer)

  lazy val responseSerializer: Serializer[List[DomainResult], SyntaxParseReply] =
    new SyntaxParseReplySerializer(resultSerializer)

  private lazy val resultSerializer = new ResultSerializer(condSerializer, errorSerializer)

  private lazy val condSerializer = new CondSerializer(syntaxTypeSerializer)

  private lazy val errorSerializer = new ErrorSerializer(posSerializer)

  private lazy val syntaxTypeSerializer = new SyntaxTypeSerializer

}
