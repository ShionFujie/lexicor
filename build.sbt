name := "lexicor"
organization := buildConfig.organization
version := buildConfig.version
scalaVersion := buildConfig.scalaVersion

lazy val coreDomain = project in file("lexicor-core-domain")