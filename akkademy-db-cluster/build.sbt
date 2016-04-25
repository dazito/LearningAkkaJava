name := """akkademy-db-cluster"""
organization := "com.dazito.java.akkademy-db-cluster"
version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
    "junit"                  %  "junit"              % "4.12"  % "test",
    "com.novocode"           %  "junit-interface"    % "0.11"  % "test",
    "com.typesafe.akka"      %% "akka-actor"         % "2.3.7",
    "com.typesafe.akka"      %% "akka-testkit"       % "2.3.7",
    "com.typesafe.akka"      %% "akka-remote"        % "2.3.7",
    "com.typesafe.akka"      %% "akka-cluster"       % "2.3.7",
    "com.typesafe.akka"      %% "akka-contrib"       % "2.3.7",
    "org.scala-lang.modules" %% "scala-java8-compat" % "0.7.0",
    "com.jason-goodwin" % "better-monads" % "0.2.1",
    "com.syncthemall" % "boilerpipe" % "1.2.2"
)

mappings in (Compile, packageBin) ~= { _.filterNot({
    case (_, name) => Seq("application.conf").contains(name)
})
}
