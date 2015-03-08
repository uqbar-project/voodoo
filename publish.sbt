releaseSettings

crossScalaVersions := Seq(scalaVersion.value)

publishTo := Some(Resolver.file("Local Maven Repository",  file(Path.userHome.absolutePath + "/.m2/repository")))