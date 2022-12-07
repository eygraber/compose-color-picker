import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  `kotlin-dsl`
}

tasks.withType<JavaCompile> {
  sourceCompatibility = libs.versions.jdk.get()
  targetCompatibility = libs.versions.jdk.get()
}

kotlin {
  jvmToolchain {
    languageVersion.set(JavaLanguageVersion.of(libs.versions.jdk.get()))
    vendor.set(JvmVendorSpec.AZUL)
  }
}

tasks.withType<KotlinCompile>().configureEach {
  kotlinOptions {
    jvmTarget = libs.versions.jdk.get()
  }
}

repositories {
  google {
    content {
      includeGroupByRegex("com\\.google.*")
      includeGroupByRegex("com\\.android.*")
      includeGroupByRegex("androidx.*")
    }
  }
  mavenCentral()
  gradlePluginPortal()
}

dependencies {
  implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
  implementation(libs.buildscript.android)
  implementation(libs.buildscript.androidCacheFix)
  implementation(libs.buildscript.atomicFu)
  implementation(libs.buildscript.compose)
  implementation(libs.buildscript.detekt)
  implementation(libs.buildscript.dokka)
  implementation(libs.buildscript.kotlin)
  implementation(libs.buildscript.ksp)
  implementation(libs.buildscript.publish)
}
