plugins {
  `kotlin-dsl`
}

repositories {
  google()
  mavenCentral()
}

dependencies {
  implementation(kotlin("gradle-plugin", version = "1.5.10"))
  implementation("com.android.tools.build:gradle:7.0.0-beta03")
  implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.17.1")
  implementation("com.vanniktech:gradle-maven-publish-plugin:0.16.0")
  implementation("org.jetbrains.dokka:dokka-gradle-plugin:1.4.32")
}
