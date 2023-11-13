ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.1.3"

lazy val root = (project in file("."))
  .settings(
    name := "spring-course"
  )



libraryDependencies += "org.planet42" %% "laika-core" % "0.19.0"
libraryDependencies += "org.planet42" %% "laika-io" % "0.19.0"
libraryDependencies += "org.planet42" %% "laika-pdf" % "0.19.0"


