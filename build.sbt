name := "lexicor"
organization := buildConfig.organization
version := buildConfig.version
scalaVersion := buildConfig.scalaVersion

lazy val coreDomain = project in file("lexicor-core-domain")

lazy val syntaxDomain = (project in file("lexicor-syntax-domain"))
  .dependsOn(coreDomain)