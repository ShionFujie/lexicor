package com.shionfujie.lexicor.lexical.implementation.di

import com.shionfujie.lexicor.lexical.implementation.LexicalParseService

final class LexicorLexicalImplementationModule(
    lexicalUsecaseModule: LexicorLexicalUsecaseModule,
    lexicalAdapterModule: LexicorLexicalAdapterModule
) {
  import lexicalAdapterModule._
  import lexicalUsecaseModule._

  lazy val lexicalParseService: LexicalParseService =
    new LexicalParseService(lexicalParser, requestDeserializer, responseSerializer)

}
