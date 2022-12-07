plugins {
  kotlin("jvm")
  id("color-compose-jetbrains")
  id("color-kotlin-library")
  id("color-detekt")
}

kotlin {
  dependencies {
    implementation(compose.desktop.currentOs)
    implementation(projects.sample.shared)
  }
}

compose.desktop {
  application {
    mainClass = "com.eygraber.compose.colorpicker.sample.ColorPickerJvmKt"
  }
}
