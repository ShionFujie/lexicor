package com.shionfujie.lexicor.core.implementation.adapter

trait Deserializer[From, To] {

  def deserialize(from: From): To

}