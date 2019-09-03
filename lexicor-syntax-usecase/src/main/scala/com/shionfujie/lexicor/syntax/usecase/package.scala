package com.shionfujie.lexicor.syntax

package object usecase {

  private[syntax] type SyntaxConstruction = SyntaxConstruction.Value

  private[syntax] implicit def convertToParsingStateExt(parsingState: ParsingState): ParsingStateExt = new ParsingStateExt(parsingState)

}
