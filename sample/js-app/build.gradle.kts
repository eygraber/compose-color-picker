import org.jetbrains.compose.ExperimentalComposeLibrary

plugins {
  id("com.eygraber.conventions-kotlin-multiplatform")
  id("com.eygraber.conventions-detekt")
  id("com.eygraber.conventions-compose-jetbrains")
}

kotlin {
  kmpTargets(
    project = project,
    js = true,
    isJsLeafModule = true
  )

  sourceSets {
    named("jsMain") {
      dependencies {
        implementation(compose.foundation)
        @OptIn(ExperimentalComposeLibrary::class)
        implementation(compose.material3)

        implementation(projects.sample.shared)
      }
    }
  }
}

compose.experimental {
  web.application {}
}
