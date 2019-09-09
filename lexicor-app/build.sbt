name := "lexicor-app"
organization := buildConfig.organization
version := buildConfig.version
scalaVersion := buildConfig.scalaVersion

libraryDependencies ++= Seq(
  deps.scalactic,
  deps.scalatest
)