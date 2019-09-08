package com.shionfujie.lexicor.lexical.implementation

import com.shionfujie.lexicor.core.domain.{Lexeme => DomainLexeme}
import com.shionfujie.lexicor.core.implementation.adapter.{Deserializer, Serializer}
import com.shionfujie.lexicor.lexical.grpc._
import com.shionfujie.lexicor.lexical.usecase.LexicalParser

import scala.concurrent.Future

class LexicalParseService(
    lexicalParser: LexicalParser,
    requestDeserializer: Deserializer[LexicalParseRequest, String],
    responseSerializer: Serializer[List[DomainLexeme], LexicalParseReply]
) extends LexicalParseServiceGrpc.LexicalParseService {

  override def parse(request: LexicalParseRequest): Future[LexicalParseReply] = {
    val input = requestDeserializer.deserialize(request)
    val lexemes = lexicalParser(input)
    val response = responseSerializer.serialize(lexemes)
    Future.successful(response)
  }

}
