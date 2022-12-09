import io.gitlab.arturbosch.detekt.Detekt
import org.gradle.accessors.dm.LibrariesForLibs

val libs = the<LibrariesForLibs>()

plugins {
  id("io.gitlab.arturbosch.detekt")
}

detekt {
  source.from("build.gradle.kts")

  autoCorrect = true
  parallel = true

  buildUponDefaultConfig = true

  config = project.files("${project.rootDir}/detekt.yml")
}

tasks.withType<Detekt>().configureEach {
  // Target version of the generated JVM bytecode. It is used for type resolution.
  jvmTarget = libs.versions.jdk.get()

  reports {
    xml.outputLocation.set(rootProject.file("build/reports/detekt/${project.name}/detekt.xml"))

    html.outputLocation.set(rootProject.file("build/reports/detekt/${project.name}.html"))
  }
}

dependencies {
  detektPlugins(libs.detekt.formatting)
  detektPlugins(libs.detektCompose)
  detektPlugins(libs.detektEygraber.formatting)
  detektPlugins(libs.detektEygraber.style)
}
