import sbt._
import Keys._
import sbt.ScalaVersion
import com.typesafe.sbteclipse.plugin.EclipsePlugin.EclipseKeys

object MyBuild extends Build {
  val Organization = "com.yukinagae"
  val Name = "scala-todoist"
  val Version = "0.0.1"
  val ScalaVersion = "2.10.3"

  lazy val project = Project(
    "scala-todoist",
    file("."),
    settings = Defaults.defaultSettings ++ Seq(
      organization := Organization,
      name := Name,
      version := Version,
      scalaVersion := ScalaVersion,
      scalacOptions := Seq("-deprecation"),
      libraryDependencies ++= Seq(
        "org.specs2" %% "specs2" % "2.3.8" % "test",
        "junit" % "junit" % "4.11" % "test"
      ),
      EclipseKeys.withSource := true,
      javacOptions in compile ++= Seq("-target", "1.7", "-source", "1.7"),
      testOptions in Test += Tests.Argument(TestFrameworks.Specs2, "junitxml", "console") //
      ))
}
