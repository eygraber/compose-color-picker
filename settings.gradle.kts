import com.eygraber.conventions.Env
import com.eygraber.conventions.repositories.addCommonRepositories

pluginManagement {
  repositories {
    google {
      content {
        includeGroupByRegex("com\\.google.*")
        includeGroupByRegex("com\\.android.*")
        includeGroupByRegex("androidx.*")
      }
    }

    maven("https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental") {
      content {
        includeGroupByRegex("org\\.jetbrains.*")
      }
    }

    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev") {
      content {
        includeGroupByRegex("org\\.jetbrains.*")
      }
    }

    mavenCentral()

    maven("https://oss.sonatype.org/content/repositories/snapshots") {
      mavenContent {
        snapshotsOnly()
      }
    }

    maven("https://s01.oss.sonatype.org/content/repositories/snapshots") {
      mavenContent {
        snapshotsOnly()
      }
    }

    gradlePluginPortal()

    maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/bootstrap")
  }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
  // comment this out for now because it doesn't work with KMP js
  // https://youtrack.jetbrains.com/issue/KT-51379/
  // repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

  repositories {
    maven("https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental") {
      content {
        includeGroupByRegex("org\\.jetbrains.*")
      }
    }

    addCommonRepositories(
      includeMavenCentral = true,
      includeMavenCentralSnapshots = true,
      includeGoogle = true,
      includeJetbrainsCompose = true,
    )

    maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/bootstrap")
    maven("https://androidx.dev/storage/compose-compiler/repository/")
  }
}

plugins {
  id("com.eygraber.conventions.settings") version "0.0.71"
  id("com.gradle.develocity") version "3.17.3"
}

rootProject.name = "compose-color-picker"

include(":library")
include(":sample:android-app")
include(":sample:jvm-app")
include(":sample:shared")
include(":sample:webApp")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

develocity {
  buildScan {
    termsOfUseUrl = "https://gradle.com/terms-of-service"
    publishing.onlyIf { Env.isCI }
    if(Env.isCI) {
      termsOfUseAgree = "yes"
    }
  }
}
