package com.shionfujie.lexicor.core.implementation.adapter

import com.shionfujie.lexicor.core.domain.{Pos => DomainPos}
import com.shionfujie.lexicor.core.grpc.{Pos => GrpcPos}

class PosAdapter extends Serializer[DomainPos, GrpcPos] with Deserializer[GrpcPos, DomainPos] {

  override def serialize(pos: DomainPos): GrpcPos = GrpcPos(start = pos._1, end = pos._2)

  override def deserialize(pos: GrpcPos): DomainPos = (pos.start, pos.end)

}
