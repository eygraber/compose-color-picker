import io.gitlab.arturbosch.detekt.Detekt

plugins {
  id("io.gitlab.arturbosch.detekt")
}

detekt {
  toolVersion = "1.17.1"

  autoCorrect = true
  parallel = true

  buildUponDefaultConfig = true

  config = project.files("${project.rootDir}/detekt.yml")
}

tasks.withType<Detekt>().configureEach {
  // Target version of the generated JVM bytecode. It is used for type resolution.
  jvmTarget = "1.8"
}

dependencies {
  detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.17.1")
  detektPlugins("com.eygraber.detekt.rules:formatting:1.0.10")
  detektPlugins("com.eygraber.detekt.rules:style:1.0.10")
}
