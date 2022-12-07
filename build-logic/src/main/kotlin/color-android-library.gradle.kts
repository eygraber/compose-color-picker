@file:Suppress("UnstableApiUsage")

import org.gradle.accessors.dm.LibrariesForLibs

val libs = the<LibrariesForLibs>()

plugins {
  id("com.android.library")
  id("org.gradle.android.cache-fix")
}

android {
  compileSdk = libs.versions.android.sdk.compile.get().toInt()

  val kmpManifestFilePath = "src/androidMain/AndroidManifest.xml"
  if(layout.projectDirectory.file(kmpManifestFilePath).asFile.exists()) {
    sourceSets.named("main") {
      manifest.srcFile(kmpManifestFilePath)
    }
  }

  val kmpResPath = "src/androidMain/res"
  if(layout.projectDirectory.file(kmpResPath).asFile.exists()) {
    sourceSets.named("main") {
      res.srcDir(kmpResPath)
    }
  }

  val kmpResourcesPath = "src/commonMain/resources"
  if(layout.projectDirectory.file(kmpResourcesPath).asFile.exists()) {
    sourceSets.named("main") {
      res.srcDir(kmpResourcesPath)
    }
  }

  defaultConfig {
    consumerProguardFile(project.file("consumer-rules.pro"))

    minSdk = libs.versions.android.sdk.min.get().toInt()

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  compileOptions {
    sourceCompatibility = JavaVersion.toVersion(libs.versions.jdk.get())
    targetCompatibility = JavaVersion.toVersion(libs.versions.jdk.get())
  }

  packagingOptions {
    resources.pickFirsts += "META-INF/*"
  }

  buildTypes {
    named("release") {
      isMinifyEnabled = false
    }
    named("debug") {
      isMinifyEnabled = false
    }
  }

  testOptions {
    unitTests {
      isIncludeAndroidResources = true
    }
  }

  publishing {
    multipleVariants {
      allVariants()
      withJavadocJar()
      withSourcesJar()
    }
  }
}
