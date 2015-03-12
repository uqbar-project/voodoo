name := "voodoo"

description := "Scala based tool to manipulate JVM bytecode"

scalaVersion := "2.11.6"

///////////////////////////////////////////////////////////////////////////////////////////////////

lazy val cacao = FDProject(
	"org.scala-lang" % "scala-reflect" % "2.11.6",
	"org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.2",
	"org.scalatest" %% "scalatest" % "2.2.4" % "test"
)

///////////////////////////////////////////////////////////////////////////////////////////////////

scalacOptions += "-feature"

unmanagedSourceDirectories in Compile := Seq((scalaSource in Compile).value)

unmanagedSourceDirectories in Test := Seq((scalaSource in Test).value)