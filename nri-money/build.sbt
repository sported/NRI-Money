name := """nri-money"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)
libraryDependencies += "org.webjars" % "bootstrap" % "3.3.4"


scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  
  
  "postgresql" % "postgresql" % "9.1-901-1.jdbc4"
)

libraryDependencies += jdbc


lazy val myProject = (project in file("."))
  .enablePlugins(PlayJava, PlayEbean)