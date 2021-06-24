plugins {
  kotlin("jvm")
  id("org.jetbrains.compose")
  detekt
  `detekt-hotfix`
}

kotlin {
  target {
    compilations.all {
      kotlinOptions {
        allWarningsAsErrors = true
        jvmTarget = "1.8"
      }
    }
  }
}

dependencies {
  implementation(compose.desktop.currentOs)
  implementation(project(":sample:app"))
}

compose.desktop {
  application {
    mainClass = "com.eygraber.compose.colorpicker.sample.DesktopAppKt"
  }
}
