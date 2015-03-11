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

compileOrder in Compile := CompileOrder.JavaThenScala

compileOrder in Test := CompileOrder.Mixed