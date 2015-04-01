name := "voodoo"

description := "Scala based tool to manipulate JVM bytecode"

scalaVersion := "2.11.6"

javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint")

initialize := {
  val _ = initialize.value
  if (sys.props("java.specification.version") != "1.8")
    sys.error("Java 8 is required for this project.")
}

///////////////////////////////////////////////////////////////////////////////////////////////////

lazy val project = FDProject(
	"org.scala-lang" % "scala-reflect" % "2.11.6",
	"org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.3",
	"org.scalatest" %% "scalatest" % "2.2.4" % "test"
)

///////////////////////////////////////////////////////////////////////////////////////////////////

scalacOptions += "-feature"

unmanagedSourceDirectories in Compile := Seq((scalaSource in Compile).value)

unmanagedSourceDirectories in Test := Seq((scalaSource in Test).value)