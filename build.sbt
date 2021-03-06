name := "Checkers"
version := "0.1"
scalaVersion := "2.13.2"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.1.2"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.2" % "test"
libraryDependencies += "org.scalatestplus" %% "junit-4-12" % "3.1.2.0" % "test"

libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "2.0.0-M1"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.8.1"

libraryDependencies += "com.google.inject" % "guice" % "4.2.1"
libraryDependencies += "net.codingwell" % "scala-guice_2.13" % "4.2.10"

libraryDependencies += "org.scalafx" %% "scalafx" % "14-R19"
resolvers += Resolver.sonatypeRepo("snapshots")

// Determine OS version of JavaFX binaries
lazy val osName = System.getProperty("os.name") match {
  case n if n.startsWith("Linux") => "linux"
  case n if n.startsWith("Mac") => "mac"
  case n if n.startsWith("Windows") => "win"
  case _ => throw new Exception("Unknown platform!")
}

// Add JavaFX dependencies
lazy val javaFXModules = Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
libraryDependencies ++= javaFXModules.map( m=>
  "org.openjfx" % s"javafx-$m" % "14.0.1" classifier osName
)

coverageExcludedPackages := ".*GUI.*;.*TUI.*;.*MockImpl.*;.*FileChooserImpl.*"