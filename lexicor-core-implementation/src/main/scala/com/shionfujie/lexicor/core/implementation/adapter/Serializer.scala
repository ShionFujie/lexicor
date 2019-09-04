package com.shionfujie.lexicor.core.implementation.adapter

trait Serializer[From, To] {

  def serialize(from: From): To

}