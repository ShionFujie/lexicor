import sbt._

object buildConfig {
  val organization = "com.shionfujie"
  val version = "0.1"
  val scalaVersion = "2.12.8"
}

object deps {
  val parserCombinator = "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2"
  val scalactic = "org.scalactic" %% "scalactic" % "3.0.8"
  val scalatest = "org.scalatest" %% "scalatest" % "3.0.8" % "test"
}