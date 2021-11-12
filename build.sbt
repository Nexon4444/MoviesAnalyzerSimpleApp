name := "app"

version := "0.1"

scalaVersion := "2.13.6"

scalacOptions ++= Seq("-language:implicitConversions", "-deprecation")
libraryDependencies ++= Seq(
  "com.novocode" % "junit-interface" % "0.11" % Test,
  ("org.apache.spark" %% "spark-core" % "3.2.0"),
  ("org.apache.spark" %% "spark-sql" % "3.2.0"),
  ("mysql" % "mysql-connector-java" % "8.0.25")
)
libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % "2.12.0"
// https://mvnrepository.com/artifact/org.apache.hadoop/hadoop-client-api
libraryDependencies += "org.apache.hadoop" % "hadoop-client-api" % "3.3.1" % "runtime"

testOptions in Test += Tests.Argument(TestFrameworks.JUnit, "-a", "-v", "-s")
mainClass in (Compile, run) := Some("Solution")
logLevel := Level.Warn