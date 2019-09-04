name := "lexicor-core-implementation"
organization := buildConfig.organization
version := buildConfig.version
scalaVersion := buildConfig.scalaVersion

scalaPBSettings.apply

libraryDependencies ++= deps.scalaPBDependencies