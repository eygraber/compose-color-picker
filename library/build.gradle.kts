import com.eygraber.colorpicker.gradle.colorTargets

plugins {
  id("color-kotlin-multiplatform")
  id("color-android-library")
  id("color-compose-jetbrains")
  id("color-detekt")
  id("color-publish")
}

android {
  namespace = "com.eygraber.compose.colorpicker"
}

kotlin {
  explicitApi()

  colorTargets()

  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation(compose.foundation)
        @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
        implementation(compose.material3)
        implementation(compose.runtime)
      }
    }

    val commonTest by getting {
      dependencies {
        implementation(kotlin("test-common"))
        implementation(kotlin("test-annotations-common"))
      }
    }
  }
}
