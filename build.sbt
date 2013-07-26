name := "app-bigbrothapp"

version := "1.0"

organization := "org.hablapps"

scalaVersion := "2.10.2"

scalaBinaryVersion <<= scalaVersion { (sv: String) => sv }

scalacOptions ++= Seq("-feature")

resolvers in ThisBuild ++= Seq(
  "Speech repo - snapshots" at "http://repo.speechlang.org/snapshots",
  "Speech repo - releases" at "http://repo.speechlang.org/releases",
  "Restlet repository" at "http://maven.restlet.org",
  "Another maven repo" at "http://mavenhub.com/")

libraryDependencies ++= Seq(
  "org.hablapps" %% "speech-web" % "0.7.1-20130726090226-8718M",
  "org.scalatest" % "scalatest_2.10" % "1.9.1" % "test",
  "junit" % "junit" % "4.10" % "test")

initialCommands in console := "import org.hablapps.{ updatable, react, speech }, updatable._"
