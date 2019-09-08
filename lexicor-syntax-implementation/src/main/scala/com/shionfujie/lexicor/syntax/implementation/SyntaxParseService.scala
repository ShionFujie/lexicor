package com.shionfujie.lexicor.syntax.implementation

import com.shionfujie.lexicor.core.domain.{Lexeme => DomainLexeme}
import com.shionfujie.lexicor.core.implementation.adapter.{Deserializer, Serializer}
import com.shionfujie.lexicor.syntax.domain.{Result => DomainResult}
import com.shionfujie.lexicor.syntax.grpc._
import com.shionfujie.lexicor.syntax.usecase.SyntaxParser

import scala.concurrent.Future

class SyntaxParseService(
    syntaxParser: SyntaxParser,
    requestDeserializer: Deserializer[SyntaxParseRequest, List[DomainLexeme]],
    responseSerializer: Serializer[List[DomainResult], SyntaxParseReply]
) extends SyntaxParseServiceGrpc.SyntaxParseService {

  override def parse(request: SyntaxParseRequest): Future[SyntaxParseReply] = {
    val lexemes = requestDeserializer.deserialize(request)
    val results = syntaxParser(lexemes)
    val response = responseSerializer.serialize(results)
    Future.successful(response)
  }

}
