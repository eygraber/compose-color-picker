plugins {
  id("com.eygraber.conventions-kotlin-multiplatform")
  id("com.eygraber.conventions-android-library")
  id("com.eygraber.conventions-compose-jetbrains")
  id("com.eygraber.conventions-detekt")
  id("com.eygraber.conventions-publish-maven-central")
}

android {
  namespace = "com.eygraber.compose.colorpicker"
}

kotlin {
  explicitApi()

  kmpTargets(
    project = project,
    android = true,
    jvm = true,
    js = true
  )

  sourceSets {
    commonMain {
      dependencies {
        implementation(compose.foundation)
        implementation(compose.material3)
        implementation(compose.runtime)
      }
    }

    commonTest {
      dependencies {
        implementation(kotlin("test-common"))
        implementation(kotlin("test-annotations-common"))
      }
    }
  }
}
