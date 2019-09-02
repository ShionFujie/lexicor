name := "lexicor-lexical-usecase"
organization := buildConfig.organization
version := buildConfig.version
scalaVersion := buildConfig.scalaVersion

libraryDependencies ++= Seq(
  deps.parserCombinator,
  deps.scalactic,
  deps.scalatest
)