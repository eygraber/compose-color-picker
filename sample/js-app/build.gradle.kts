plugins {
  id("com.eygraber.conventions-kotlin-multiplatform")
  id("com.eygraber.conventions-detekt")
  id("com.eygraber.conventions-compose-jetbrains")
}

kotlin {
  kmpTargets(
    project = project,
    js = true,
    binaryType = BinaryType.Executable,
  )

  sourceSets {
    jsMain {
      dependencies {
        implementation(compose.foundation)
        implementation(compose.material3)

        implementation(projects.sample.shared)
      }
    }
  }
}

compose.experimental {
  web.application {}
}
