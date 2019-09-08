package com.shionfujie.lexicor.syntax.implementation.di

import com.shionfujie.lexicor.syntax.implementation.SyntaxParseService

final class LexicorSyntaxImplementationModule(
    usecaseModule: LexicorSyntaxUsecaseModule,
    adapterModule: LexicorSyntaxAdapterModule
) {

  import adapterModule._
  import usecaseModule._

  lazy val parseService: SyntaxParseService =
    new SyntaxParseService(syntaxParser, requestDeserializer, responseSerializer)

}
