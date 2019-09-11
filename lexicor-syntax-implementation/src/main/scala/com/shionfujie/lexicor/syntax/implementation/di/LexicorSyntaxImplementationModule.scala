package com.shionfujie.lexicor.syntax.implementation.di

import com.shionfujie.lexicor.syntax.implementation.SyntaxParseService

final class LexicorSyntaxImplementationModule(
    syntaxUsecaseModule: LexicorSyntaxUsecaseModule,
    syntaxAdapterModule: LexicorSyntaxAdapterModule
) {

  import syntaxAdapterModule._
  import syntaxUsecaseModule._

  lazy val syntaxParseService: SyntaxParseService =
    new SyntaxParseService(syntaxParser, requestDeserializer, responseSerializer)

}
