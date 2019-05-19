name := """prime-factorizations-play"""

version := "2.7.x"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.8"

libraryDependencies += guice
libraryDependencies += "com.typesafe.play" %% "play-slick" % "4.0.1"
libraryDependencies += "com.typesafe.play" %% "play-slick-evolutions" % "4.0.1"
libraryDependencies += "org.scalatest" % "scalatest_2.12" % "3.0.5" % "test"
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.0" % "test"

libraryDependencies += "com.h2database" % "h2" % "1.4.199"

libraryDependencies += specs2 % Test

scalacOptions ++= Seq(
  "-feature",
  "-deprecation",
  "-Xfatal-warnings"
)
