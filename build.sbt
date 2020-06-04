import sbt.Def
import sbtassembly.MergeStrategy

name := "ScalaAkkaHttp_StartingPoint"
version := "0.1"
organization := "mveeprojects"
scalaVersion := "2.13.1"

lazy val akkaVersion = "2.6.3"
lazy val akkaHttpVersion = "10.1.11"
lazy val scalaLoggingVersion = "3.9.2"
lazy val logbackVersion = "1.2.3"
lazy val kamonVersion = "2.1.0"
lazy val gatlingVersion = "3.3.1"
lazy val scalaTestVersion = "3.0.8"
lazy val restAssuredVersion = "4.1.1"

val testingDependencies = Seq(
  "org.scalatest" %% "scalatest" % scalaTestVersion % Test,
  "io.rest-assured" % "rest-assured" % restAssuredVersion % Test
)

val apiDependencies: Def.Setting[Seq[ModuleID]] = libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
  "ch.qos.logback" % "logback-classic" % logbackVersion,
  "io.kamon" %% "kamon-core" % kamonVersion,
  "io.kamon" %% "kamon-akka-http" % kamonVersion,
  "io.kamon" %% "kamon-system-metrics" % kamonVersion exclude("org.slf4j", "slf4j-api"),
  "io.kamon" %% "kamon-prometheus" % kamonVersion
) ++ testingDependencies

val performanceDependencies: Def.Setting[Seq[ModuleID]] = libraryDependencies ++= Seq(
  "io.gatling" % "gatling-core" % gatlingVersion,
  "io.gatling" % "gatling-app" % gatlingVersion,
  "io.gatling.highcharts" % "gatling-charts-highcharts" % gatlingVersion
)

lazy val mergeStrategy = assemblyMergeStrategy in assembly := {
  case PathList("META-INF", _*) => MergeStrategy.discard
  case PathList("reference.conf") => MergeStrategy.concat
  case _ => MergeStrategy.first
}

lazy val apiAssemblySettings = Seq(mergeStrategy, mainClass in assembly := Some("Application"))

lazy val performanceAssemblySettings = Seq(mergeStrategy, mainClass in assembly := Some("PerformanceMain"))

lazy val api: Project = (project in file("api"))
  .settings(apiDependencies: _*)
  .settings(apiAssemblySettings: _*)

lazy val performance: Project = (project in file("performance"))
  .settings(performanceDependencies: _*)
  .settings(performanceAssemblySettings: _*)

lazy val root: Project = (project in file("."))
  .aggregate(api, performance)
