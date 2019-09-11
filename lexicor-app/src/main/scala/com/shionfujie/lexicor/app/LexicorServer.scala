package com.shionfujie.lexicor.app

import java.util.logging.Logger

import com.shionfujie.lexicor.lexical.grpc.LexicalParseServiceGrpc
import com.shionfujie.lexicor.lexical.implementation.LexicalParseService
import com.shionfujie.lexicor.syntax.grpc.SyntaxParseServiceGrpc
import com.shionfujie.lexicor.syntax.implementation.SyntaxParseService
import io.grpc.{Server, ServerBuilder}

import scala.concurrent.ExecutionContext

class LexicorServer(
    executionContext: ExecutionContext,
    logger: Logger,
    port: Int,
    lexicalParseService: LexicalParseService,
    syntaxParseService: SyntaxParseService
) { self =>

  private val server: Server = ServerBuilder
    .forPort(port)
    .addService(SyntaxParseServiceGrpc.bindService(syntaxParseService, executionContext))
    .addService(LexicalParseServiceGrpc.bindService(lexicalParseService, executionContext))
    .build()

  def start(): Unit = {
    logger.info("Server started, listening on " + port)
    server.start()
    sys.addShutdownHook {
      System.err.println("*** shutting down gRPC server since JVM is shutting down")
      self.stop()
      System.err.println("*** server shut down")
    }
  }

  def blockUntilShutdown(): Unit =
    server.awaitTermination()

  private def stop(): Unit =
    server.shutdown()

}
