package com.shionfujie.lexicor.lexical.implementation.adapter

import com.shionfujie.lexicor.core.implementation.adapter.Deserializer
import com.shionfujie.lexicor.lexical.grpc.LexicalParseRequest

class LexicalParseRequestDeserializer extends Deserializer[LexicalParseRequest, String] {

  override def deserialize(input: LexicalParseRequest): String = input.input

}
