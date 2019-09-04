package com.shionfujie.lexicor.core.implementation.di

import com.shionfujie.lexicor.core.domain.{Lexeme => DomainLexeme, Pos => DomainPos}
import com.shionfujie.lexicor.core.grpc.{Lexeme => GrpcLexeme, Pos => GrpcPos}
import com.shionfujie.lexicor.core.implementation.adapter._

final class LexicorCoreAdapterModule {

  lazy val posDeserializer: Deserializer[GrpcPos, DomainPos] = posAdapter

  lazy val posSerializer: Serializer[DomainPos, GrpcPos] = posAdapter

  lazy val lexemeDeserializer: Deserializer[GrpcLexeme, DomainLexeme] = lexemeAdapter

  lazy val lexemeSerializer: Serializer[DomainLexeme, GrpcLexeme] = lexemeAdapter

  private lazy val posAdapter = new PosAdapter

  private lazy val lexemeAdapter = new LexemeAdapter(posAdapter)

}
