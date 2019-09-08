package com.shionfujie.lexicor.lexical.implementation.di

import com.shionfujie.lexicor.lexical.implementation.LexicalParseService

final class LexicorLexicalImplementationModule(
    usecaseModule: LexicorLexicalUsecaseModule,
    adapterModule: LexicorLexicalAdapterModule
) {
  import adapterModule._
  import usecaseModule._

  lazy val lexicalParseService: LexicalParseService =
    new LexicalParseService(lexicalParser, requestDeserializer, responseSerializer)

}
