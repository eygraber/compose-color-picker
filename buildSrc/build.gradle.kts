plugins {
  `kotlin-dsl`
}

repositories {
  google()
  mavenCentral()
}

dependencies {
  implementation(kotlin("gradle-plugin", version = "1.5.31"))
  implementation("com.android.tools.build:gradle:7.0.2")
  implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.18.1")
  implementation("com.vanniktech:gradle-maven-publish-plugin:0.18.0")
  implementation("org.jetbrains.dokka:dokka-gradle-plugin:1.5.31")
}
