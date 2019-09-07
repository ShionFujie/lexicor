package com.shionfujie.lexicor.syntax.implementation.adapter

import com.shionfujie.lexicor.core.implementation.adapter.Serializer
import com.shionfujie.lexicor.syntax.domain.{Cond => DomainCond, SyntaxType => DomainSyntaxType}
import com.shionfujie.lexicor.syntax.grpc.{Cond => GrpcCond, SyntaxType => GrpcSyntaxType}

class CondSerializer(syntaxTypeSerializer: Serializer[DomainSyntaxType, GrpcSyntaxType])
  extends Serializer[DomainCond, GrpcCond] {

  override def serialize(cond: DomainCond): GrpcCond =
    GrpcCond(syntaxType = syntaxTypeSerializer.serialize(cond.syntaxType), value = cond.value)

}
