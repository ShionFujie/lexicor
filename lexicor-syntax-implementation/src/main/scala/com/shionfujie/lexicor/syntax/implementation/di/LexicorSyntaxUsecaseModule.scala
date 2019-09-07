package com.shionfujie.lexicor.syntax.implementation.di

import com.shionfujie.lexicor.syntax.usecase.SyntaxParser

final class LexicorSyntaxUsecaseModule {

  lazy val syntaxParser: SyntaxParser = new SyntaxParser

}
