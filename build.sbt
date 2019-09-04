name := "lexicor"
organization := buildConfig.organization
version := buildConfig.version
scalaVersion := buildConfig.scalaVersion

lazy val coreImplementation = (project in file("lexicor-core-implementation"))
  .dependsOn(coreDomain)

lazy val lexicalUsecase = (project in file("lexicor-lexical-usecase"))
  .dependsOn(coreDomain)

lazy val syntaxUsecase = (project in file("lexicor-syntax-usecase"))
  .dependsOn(syntaxDomain)

lazy val coreDomain = project in file("lexicor-core-domain")

lazy val syntaxDomain = (project in file("lexicor-syntax-domain"))
  .dependsOn(coreDomain)