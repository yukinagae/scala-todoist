import sbt._
import Keys._
import sbt.ScalaVersion
import com.typesafe.sbteclipse.plugin.EclipsePlugin.EclipseKeys

object BuildSettings {
  val buildSettings = Defaults.defaultSettings ++ Seq(
    organization := "com.yukinagae",
    version := "0.0.1",
    scalaVersion := "2.10.3",
    scalacOptions := Seq("-deprecation"),
    libraryDependencies ++= Seq(
      "org.apache.httpcomponents" % "httpclient" % "4.3.1" % "provided",
      "org.specs2" %% "specs2" % "2.3.8" % "test",
      "junit" % "junit" % "4.11" % "test" //
      ),
    EclipseKeys.withSource := true,
    javacOptions in compile ++= Seq("-target", "1.7", "-source", "1.7"),
    testOptions in Test += Tests.Argument(TestFrameworks.Specs2, "junitxml", "console") //
    )
}

object ScalaMacroDebugBuild extends Build {
  import BuildSettings._
  import sbtassembly.Plugin.AssemblyKeys._

  lazy val root: Project = Project(
    "scala-todoist",
    file("."),
    settings = buildSettings ++ Seq(publishArtifact := false)).aggregate(macro, scala_todoist)

  lazy val macro: Project = Project(
    "macro",
    file("macro"),
    settings = buildSettings ++ Seq(
      libraryDependencies <+= (scalaVersion)("org.scala-lang" % "scala-reflect" % _)))

  lazy val scala_todoist: Project = Project(
    "scala_todoist",
    file("scala_todoist"),
    settings = buildSettings ++
    sbtassembly.Plugin.assemblySettings ++
    addArtifact(Artifact("scala-todoist", "assembly"), assembly) ++
    Seq(
      test in assembly := {},
      assemblyOption in assembly ~= { _.copy(includeScala = false) },
      jarName in assembly := "scala-todoist.jar"
    )).dependsOn(macro)
}
