name := """nri-money"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)
libraryDependencies += "org.webjars" % "bootstrap" % "3.3.4"


scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "mysql" % "mysql-connector-java" % "5.1.18"
  
 
)

libraryDependencies += jdbc


lazy val myProject = (project in file("."))
  .enablePlugins(PlayJava, PlayEbean)