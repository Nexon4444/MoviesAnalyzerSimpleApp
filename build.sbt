name := "app"

version := "0.1"

scalaVersion := "2.13.6"



scalacOptions ++= Seq("-language:implicitConversions", "-deprecation")
libraryDependencies ++= Seq(
  "com.novocode" % "junit-interface" % "0.11" % Test,
  ("org.apache.spark" %% "spark-core" % "3.2.0"),
  ("org.apache.spark" %% "spark-sql" % "3.2.0"),
//  ("org.apache.spark" %% "spark-mllib" % "1.6.2"),
  ("mysql" % "mysql-connector-java" % "8.0.25")
)
//dependencyOverrides ++= Seq(
//  ("com.fasterxml.jackson.core" % "jackson-databind" % "jackson-annotations" % "2.13.0")
//)
// https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind
libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % "2.12.0"

testOptions in Test += Tests.Argument(TestFrameworks.JUnit, "-a", "-v", "-s")
// https://mvnrepository.com/artifact/mysql/mysql-connector-java
//libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.25"