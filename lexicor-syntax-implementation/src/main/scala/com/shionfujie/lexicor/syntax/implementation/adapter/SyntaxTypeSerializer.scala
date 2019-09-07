package com.shionfujie.lexicor.syntax.implementation.adapter

import com.shionfujie.lexicor.core.implementation.adapter.Serializer
import com.shionfujie.lexicor.syntax.domain.{SyntaxType => DomainSyntaxType}
import com.shionfujie.lexicor.syntax.grpc.{SyntaxType => GrpcSyntaxType}

class SyntaxTypeSerializer extends Serializer[DomainSyntaxType, GrpcSyntaxType] {

  override def serialize(syntaxType: DomainSyntaxType): GrpcSyntaxType = syntaxType match {
    case DomainSyntaxType.TagIs   => GrpcSyntaxType.TAG_IS
    case DomainSyntaxType.TagIsIn => GrpcSyntaxType.TAG_IS_IN
  }

}
