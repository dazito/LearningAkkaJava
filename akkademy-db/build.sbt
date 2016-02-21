name := """akkademy-db"""

version := "1.0"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
    "junit"             % "junit"           % "4.12"  % "test",
    "com.novocode"      % "junit-interface" % "0.11"  % "test",
    "com.typesafe.akka" %% "akka-actor" % "2.4.2",
    "com.typesafe.akka" %% "akka-testkit" % "2.4.2",
    "org.scala-lang.modules" %% "scala-java8-compat" % "0.6.0"
)
