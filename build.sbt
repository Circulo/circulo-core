name := """circulo-core"""

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.11.4"

val springVersion = "4.0.7.RELEASE"

//include conf directory for testing
unmanagedClasspath in Test += baseDirectory.value / "conf"

dependencyOverrides ++= Set(
  "org.springframework" % "spring-test" % springVersion,
  "org.springframework" % "spring-tx" % springVersion,
  "org.springframework" % "spring-jdbc" % springVersion,
  "org.springframework" % "spring-context" % springVersion,
  "org.springframework" % "spring-expression" % springVersion,
  "org.springframework" % "spring-aop" % springVersion,
  "org.springframework" % "spring-beans" % springVersion,
  "org.springframework" % "spring-core" % springVersion,
  "com.google.guava" % "guava" % "18.0",
  "org.javassist" % "javassist" % "3.18.1-GA"
)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.3.4",
  "com.typesafe.akka" %% "akka-kernel" % "2.3.4",
  "com.typesafe.akka" %% "akka-testkit" % "2.3.4" % "test",
  "com.typesafe.akka" %% "akka-slf4j" % "2.3.4",
  "com.typesafe.akka" %% "akka-camel" % "2.3.8",
  "org.apache.camel" % "camel-jetty" % "2.7.1",
  "mysql" % "mysql-connector-java" % "5.1.30",
  "javax.inject" % "javax.inject" % "1",
  "org.springframework.batch" % "spring-batch-core" % "3.0.1.RELEASE" withSources(),
  "org.springframework.batch" % "spring-batch-test" % "3.0.1.RELEASE" withSources(),
  "org.springframework.boot" % "spring-boot-starter-batch" % "1.1.8.RELEASE",
  "org.springframework" % "spring-oxm" % springVersion,
  "org.springframework" % "spring-orm" % springVersion,
  "org.springframework" % "spring-tx" % springVersion,
  "org.springframework" % "spring-test" % springVersion,
  "org.springframework.data" % "spring-data-jpa" % "1.8.0.RELEASE",
  "org.springframework.data" % "spring-data-commons" % "1.10.0.RELEASE",
  "org.springframework.data" % "spring-data-mongodb" % "1.7.0.RELEASE",
  "org.mongodb" % "mongo-java-driver" % "3.0.1",
  "org.kubek2k" % "springockito" % "1.0.9",
  "org.kubek2k" % "springockito-annotations" % "1.0.9",
  "org.hibernate" % "hibernate-entitymanager" % "4.3.7.Final",
  "org.hibernate" % "hibernate-core" % "4.3.7.Final",
  "org.hibernate" % "hibernate-validator" % "5.1.3.Final",
  "org.hibernate" % "hibernate-validator-cdi" % "5.1.3.Final",
  "javax.el" % "javax.el-api" % "2.2.4",
  "com.google.code.gson" % "gson" % "2.3.1",
  "com.google.guava" % "guava" % "18.0",
  "javax.validation" % "validation-api" % "1.1.0.Final",
  "com.novocode" % "junit-interface" % "0.11" % "test",
  "ch.qos.logback" % "logback-classic" % "1.1.3",
  "org.apache.httpcomponents" % "httpclient" % "4.3.6",
  "net.sourceforge.htmlcleaner" % "htmlcleaner" % "2.13",
  "org.jsoup" % "jsoup" % "1.8.3"
)
