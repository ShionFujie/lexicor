package com.shionfujie.lexicor.syntax.implementation.adapter

import com.shionfujie.lexicor.core.implementation.adapter.Serializer
import com.shionfujie.lexicor.syntax.domain.{Result => DomainResult}
import com.shionfujie.lexicor.syntax.grpc.{SyntaxParseReply, Result => GrpcResult}

class SyntaxParseReplySerializer(resultSerializer: Serializer[DomainResult, GrpcResult])
  extends Serializer[List[DomainResult], SyntaxParseReply] {

  override def serialize(results: List[DomainResult]): SyntaxParseReply =
    SyntaxParseReply(results.map(resultSerializer.serialize))

}
