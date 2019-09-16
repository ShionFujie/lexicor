package com.shionfujie.lexicor.app
import com.shionfujie.lexicor.app.di.LexicorAppComponent

object App {

  def main(args: Array[String]): Unit = {
    val server = LexicorAppComponent().lexicorServer
    server.start()
    server.blockUntilShutdown()
  }

}
