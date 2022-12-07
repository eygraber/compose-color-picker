import com.eygraber.colorpicker.gradle.colorTargets
import org.jetbrains.compose.ExperimentalComposeLibrary

plugins {
  id("color-kotlin-multiplatform")
  id("color-detekt")
  id("color-compose-jetbrains")
}

kotlin {
  colorTargets(
    js = true,
    isJsLeafModule = true,
    android = false,
    jvm = false,
    ios = false
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
