import sbt._
import sbt.Keys.sourceManaged
import sbtprotoc.ProtocPlugin.autoImport.PB

object buildConfig {
  val organization = "com.shionfujie"
  val version = "0.1"
  val scalaVersion = "2.12.8"
}

object deps {
  val parserCombinator = "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2"
  val scalactic = "org.scalactic" %% "scalactic" % "3.0.8"
  val scalatest = "org.scalatest" %% "scalatest" % "3.0.8" % "test"
  val scalaPBDependencies = Seq(
    "io.grpc" % "grpc-netty" % scalapb.compiler.Version.grpcJavaVersion,
    "com.thesamet.scalapb" %% "scalapb-runtime-grpc" % scalapb.compiler.Version.scalapbVersion
  )
}

object scalaPBSettings {
  def apply = Seq(
    PB.targets in Compile :=
      Seq(scalapb.gen(flatPackage = true) -> (sourceManaged in Compile).value)
  )
}