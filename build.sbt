name := "lexicor"
organization := buildConfig.organization
version := buildConfig.version
scalaVersion := buildConfig.scalaVersion

lazy val coreImplementation = (project in file("lexicor-core-implementation"))
  .dependsOn(coreDomain)

lazy val lexicalImplementation = (project in file("lexicor-lexical-implementation"))
  .settings(includeProtos(coreImplementation))
  .dependsOn(
    lexicalUsecase,
    coreImplementation
  )

lazy val lexicalUsecase = (project in file("lexicor-lexical-usecase"))
  .dependsOn(coreDomain)

lazy val syntaxUsecase = (project in file("lexicor-syntax-usecase"))
  .dependsOn(syntaxDomain)

lazy val coreDomain = project in file("lexicor-core-domain")

lazy val syntaxDomain = (project in file("lexicor-syntax-domain"))
  .dependsOn(coreDomain)

def includeProtos(project: Project) =
  PB.includePaths in Compile += project.base / "src" / "main" / "protobuf"