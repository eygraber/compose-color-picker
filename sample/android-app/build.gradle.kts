plugins {
  kotlin("android")
  id("com.android.application")
  id("com.eygraber.conventions-kotlin-library")
  id("com.eygraber.conventions-detekt")
  id("com.eygraber.conventions-compose-jetpack")
}

android {
  namespace = "com.eygraber.compose.colorpicker.sample"

  compileSdk = libs.versions.android.sdk.compile.get().toInt()

  defaultConfig {
    applicationId = "com.eygraber.compose.colorpicker.sample"
    targetSdk = libs.versions.android.sdk.target.get().toInt()
    minSdk = libs.versions.android.sdk.min.get().toInt()

    versionCode = 1
    versionName = "1.0.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    multiDexEnabled = true
  }

  buildTypes {
    named("release") {
      isMinifyEnabled = true
      isShrinkResources = true
      proguardFiles.clear()
      proguardFiles += project.file("proguard-rules.pro")
    }

    named("debug") {
      applicationIdSuffix = ".debug"

      versionNameSuffix = "-DEBUG"

      isMinifyEnabled = false
    }
  }

  compileOptions {
    isCoreLibraryDesugaringEnabled = true
    sourceCompatibility = JavaVersion.toVersion(libs.versions.jdk.get())
    targetCompatibility = JavaVersion.toVersion(libs.versions.jdk.get())
  }

  @Suppress("UnstableApiUsage")
  packaging {
    resources.pickFirsts += "META-INF/*"
  }

  dependencies {
    implementation(projects.sample.shared)

    coreLibraryDesugaring(libs.android.desugar)

    with(libs.androidx) {
      implementation(activity)
      implementation(activityCompose)
      implementation(appCompat)

      with(compose) {
        implementation(foundation)
        implementation(material3)
        debugImplementation(uiTooling)
        implementation(uiToolingPreview)
      }
    }
  }
}
