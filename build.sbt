name := "koffio-uda"

version := "1.0"

scalaVersion := "2.11.8"

// we do not need test classes in the result jar
test in assembly := {}

assemblyJarName in assembly := "hll.jar"

// define an output name of the result jar in the root folder. A bit easier for docker to find it there
target in assembly := new java.io.File(".")

//we do not need the scala library in the result jar
assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)

// compile java code with compatibility with JDK 7 which is used for cassandra inside Docker
javacOptions ++= Seq("-source", "1.7", "-target", "1.7")

// the library for HLL counters with exclusion of junit
libraryDependencies += "com.github.prasanthj" % "hyperloglog" % "cec4bb8980" exclude("junit", "junit")
// exclude the junit dependency from the result jar
libraryDependencies += "junit" % "junit" % "4.12" % "provided"
