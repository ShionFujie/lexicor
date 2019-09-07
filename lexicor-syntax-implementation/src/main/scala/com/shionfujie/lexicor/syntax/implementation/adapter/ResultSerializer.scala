package com.shionfujie.lexicor.syntax.implementation.adapter

import com.shionfujie.lexicor.core.implementation.adapter.Serializer
import com.shionfujie.lexicor.syntax.domain.{Cond => DomainCond, Error => DomainError, Result => DomainResult}
import com.shionfujie.lexicor.syntax.grpc.{Cond => GrpcCond, Error => GrpcError, Result => GrpcResult}

class ResultSerializer(
                        condSerializer: Serializer[DomainCond, GrpcCond],
                        errorSerializer: Serializer[DomainError, GrpcError]
                      ) extends Serializer[DomainResult, GrpcResult] {

  override def serialize(result: DomainResult): GrpcResult = result match {
    case Left(error) => GrpcResult().withError(errorSerializer.serialize(error))
    case Right(cond) => GrpcResult().withCond(condSerializer.serialize(cond))
  }

}
