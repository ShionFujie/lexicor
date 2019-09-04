resolvers += Resolver.bintrayRepo("sbt", "sbt-plugin-releases")
addSbtPlugin("com.thesamet" % "sbt-protoc" % "0.99.23")

libraryDependencies += "com.thesamet.scalapb" %% "compilerplugin" % "0.9.0"
