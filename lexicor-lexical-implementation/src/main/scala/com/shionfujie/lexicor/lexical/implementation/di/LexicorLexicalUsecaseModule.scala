package com.shionfujie.lexicor.lexical.implementation.di

import com.shionfujie.lexicor.lexical.usecase.LexicalParser

final class LexicorLexicalUsecaseModule {

  lazy val lexicalParser: LexicalParser = new LexicalParser

}
