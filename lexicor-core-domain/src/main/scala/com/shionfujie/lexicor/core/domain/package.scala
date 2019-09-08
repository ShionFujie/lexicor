package com.shionfujie.lexicor.core

package object domain {

  type With[A, B] = (B, A)

  type Pos = (Int, Int)

  class PosExt(pos: Pos) {

    val start: Int = pos._1

    val end: Int = pos._2

    def overflow: Pos = (end + 1, end + 2)

    def bridgeTo(that: Pos): Pos = (this.start, that.end)

    def isAdjacentTo(that: Pos): Boolean = this.end + 1 == that.start

  }

  implicit def convertToPosExt(pos: Pos): PosExt = new PosExt(pos)

}
