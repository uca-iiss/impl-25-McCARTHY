name := "EjemploHerencia"

version := "0.1"

scalaVersion := "2.13.9"

ThisBuild / organization := "equipo.mccarthy"

ThisBuild / versionScheme := Some("early-semver")

Compile / run / mainClass := Some("Main")

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.17" % Test

