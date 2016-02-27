name := """akkademy-db"""
organization := "com.dazito.java.akkademy-db"
version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
    "junit"                  %  "junit"           % "4.12"  % "test",
    "com.novocode"           %  "junit-interface" % "0.11"  % "test",
    "com.typesafe.akka"      %% "akka-actor" % "2.4.2",
    "com.typesafe.akka"      %% "akka-testkit" % "2.4.2",
    "com.typesafe.akka"      %% "akka-remote" % "2.4.2",
    "org.scala-lang.modules" %% "scala-java8-compat" % "0.6.0"
)

mappings in (Compile, packageBin) ~= { _.filterNot({
    case (_, name) => Seq("application.conf").contains(name)
})
}
