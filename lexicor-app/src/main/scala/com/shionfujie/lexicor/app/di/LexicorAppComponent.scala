package com.shionfujie.lexicor.app.di
import java.util.logging.Logger

import com.shionfujie.lexicor.app.LexicorServer
import com.shionfujie.lexicor.core.implementation.di.LexicorCoreAdapterModule
import com.shionfujie.lexicor.lexical.implementation.di._
import com.shionfujie.lexicor.syntax.implementation.di._

import scala.concurrent.ExecutionContext

class LexicorAppComponent private (
    lexicalModule: LexicorLexicalImplementationModule,
    syntaxModule: LexicorSyntaxImplementationModule
) {

  lazy val lexicorServer: LexicorServer = new LexicorServer(
    executionContext = ExecutionContext.global,
    logger = Logger.getLogger(classOf[LexicorServer].getName),
    port = 50051,
    lexicalParseService = lexicalModule.lexicalParseService,
    syntaxParseService = syntaxModule.syntaxParseService
  )

}

object LexicorAppComponent {

  def apply(): LexicorAppComponent = {
    val coreAdapterModule = new LexicorCoreAdapterModule
    new LexicorAppComponent(
      lexicalModule = new LexicorLexicalImplementationModule(
        lexicalUsecaseModule = new LexicorLexicalUsecaseModule,
        lexicalAdapterModule = new LexicorLexicalAdapterModule(coreAdapterModule)
      ),
      syntaxModule = new LexicorSyntaxImplementationModule(
        syntaxUsecaseModule = new LexicorSyntaxUsecaseModule,
        syntaxAdapterModule = new LexicorSyntaxAdapterModule(coreAdapterModule)
      )
    )
  }

}
