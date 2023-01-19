import com.eygraber.conventions.tasks.deleteRootBuildDirWhenCleaning
import org.jetbrains.kotlin.config.AnalysisFlags.explicitApiMode
import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode
import org.jetbrains.kotlin.js.translate.context.Namer.kotlin

buildscript {
  dependencies {
    classpath(libs.buildscript.android)
    classpath(libs.buildscript.androidCacheFix)
    classpath(libs.buildscript.compose)
    classpath(libs.buildscript.detekt)
    classpath(libs.buildscript.dokka)
    classpath(libs.buildscript.kotlin)
    classpath(libs.buildscript.publish)
  }
}

plugins {
  base
  alias(libs.plugins.conventions)
}

deleteRootBuildDirWhenCleaning()

gradleConventionsDefaults {
  android {
    sdkVersions(
      compileSdk = libs.versions.android.sdk.compile,
      targetSdk = libs.versions.android.sdk.target,
      minSdk = libs.versions.android.sdk.min
    )
  }

  compose {
    // because we use js
    applyToAndroidAndJvmOnly = false

    overrideAndroidComposeVersions(
      compilerVersion = libs.versions.composeAndroid.compiler,
      bomVersion = libs.versions.composeAndroid.bom
    )

    useAndroidComposeCompilerVersionForJetbrainsComposeCompilerVersion = true
  }

  detekt {
    plugins(
      libs.detekt.formatting,
      libs.detektCompose,
      libs.detektEygraber.formatting,
      libs.detektEygraber.style
    )
  }

  kotlin {
    jdkVersion = libs.versions.jdk.get()
    jvmDistribution = JvmVendorSpec.AZUL
  }
}
