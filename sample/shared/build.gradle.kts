import com.eygraber.colorpicker.gradle.colorTargets

plugins {
  id("color-kotlin-multiplatform")
  id("color-android-library")
  id("color-compose-jetbrains")
  id("color-detekt")
}

android {
  namespace = "com.eygraber.compose.colorpicker.sample"
}

kotlin {
  colorTargets()

  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation(projects.library)

        api(compose.foundation)
        @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
        api(compose.material3)
        api(compose.runtime)
      }
    }
  }
}
