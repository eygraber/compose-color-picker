plugins {
  id("com.android.application")
  kotlin("android")
  id("org.jetbrains.compose")
  detekt
  `detekt-hotfix`
}

android {
  compileSdk = 31

  defaultConfig {
    applicationId = "com.eygraber.compose.colorpicker.sample"
    minSdk = 24
    targetSdk = 31
    versionCode = 1
    versionName = "1.0"
  }

  compileOptions {
    isCoreLibraryDesugaringEnabled = true
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }

  buildTypes {
    getByName("release") {
      isMinifyEnabled = false
    }
  }

  kotlinOptions {
    jvmTarget = "11"
  }
}

dependencies {
  implementation(project(":sample:app"))
  implementation("androidx.core:core-ktx:1.7.0-beta02")
  implementation("androidx.appcompat:appcompat:1.4.0-beta01")
  implementation("androidx.activity:activity-compose:1.4.0-beta01") {
    exclude(group = "androidx.compose.animation")
    exclude(group = "androidx.compose.foundation")
    exclude(group = "androidx.compose.material")
    exclude(group = "androidx.compose.runtime")
    exclude(group = "androidx.compose.ui")
  }
  implementation("androidx.compose.ui:ui-tooling:1.1.0-alpha05") {
    exclude(group = "androidx.compose.animation")
    exclude(group = "androidx.compose.foundation")
    exclude(group = "androidx.compose.material")
    exclude(group = "androidx.compose.runtime")
    exclude(group = "androidx.compose.ui")
  }

  coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")
}
