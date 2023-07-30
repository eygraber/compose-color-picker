plugins {
  id("com.eygraber.conventions-kotlin-multiplatform")
  id("com.eygraber.conventions-android-library")
  id("com.eygraber.conventions-compose-jetbrains")
  id("com.eygraber.conventions-detekt")
}

android {
  namespace = "com.eygraber.compose.colorpicker.sample"
}

kotlin {
  kmpTargets(
    project = project,
    android = true,
    jvm = true,
    js = true
  )

  sourceSets {
    commonMain {
      dependencies {
        implementation(projects.library)

        api(compose.foundation)
        api(compose.material3)
        api(compose.runtime)
      }
    }
  }
}
