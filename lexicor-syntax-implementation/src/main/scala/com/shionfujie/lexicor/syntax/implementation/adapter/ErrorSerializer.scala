package com.shionfujie.lexicor.syntax.implementation.adapter

import com.shionfujie.lexicor.core.domain.{Pos => DomainPos}
import com.shionfujie.lexicor.core.grpc.{Pos => GrpcPos}
import com.shionfujie.lexicor.core.implementation.adapter.Serializer
import com.shionfujie.lexicor.syntax.domain.{Error => DomainError}
import com.shionfujie.lexicor.syntax.grpc.{Error => GrpcError}

class ErrorSerializer(posSerializer: Serializer[DomainPos, GrpcPos])
  extends Serializer[DomainError, GrpcError] {

  override def serialize(error: DomainError): GrpcError =
    GrpcError(at = Some(posSerializer.serialize(error.at)), message = error.message)

}
