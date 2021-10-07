plugins {
  id("com.android.library")
  kotlin("multiplatform")
  id("org.jetbrains.compose")
  detekt
  `detekt-hotfix`
}

android {
  configurations {
    create("testApi")
    create("testDebugApi")
    create("testReleaseApi")
  }

  compileSdk = 31

  defaultConfig {
    minSdk = 24
    targetSdk = 31
  }

  buildTypes {
    getByName("release") {
      isMinifyEnabled = false
    }
  }

  compileOptions {
    isCoreLibraryDesugaringEnabled = true
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }

  packagingOptions {
    resources.pickFirsts += "META-INF/*"
  }

  dependencies {
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")
  }
}

kotlin {
  android()
  jvm("desktop").compilations.all {
    compileKotlinTask.sourceCompatibility = "11"
    compileKotlinTask.targetCompatibility = "11"

    kotlinOptions {
      allWarningsAsErrors = true
      jvmTarget = "11"
      freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn")
    }
  }

  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation(project(":library"))
        api(compose.runtime)
        api(compose.foundation)
        api(compose.material)
        api(compose.materialIconsExtended)
      }
    }

    val androidMain by getting {
      dependsOn(commonMain)
    }

    val desktopMain by getting {
      dependsOn(commonMain)
    }
  }

  targets.configureEach {
    compilations.configureEach {
      kotlinOptions {
        freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn")
      }
    }
  }
}
