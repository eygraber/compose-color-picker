package com.eygraber.colorpicker.gradle

import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.KotlinCompilationData

fun KotlinMultiplatformExtension.colorTargets(
  jvm: Boolean = true,
  android: Boolean = true,
  js: Boolean = true,
  isJsLeafModule: Boolean = false,
  jsModuleName: String? = null,
  ios: Boolean = false
) {
  if(jvm) {
    jvm()
  }

  if(android) {
    android {
      publishLibraryVariants("release")
    }
  }

  if(js) {
    js(IR) {
      if(jsModuleName != null) {
        moduleName = jsModuleName
      }

      browser {
        if(isJsLeafModule) {
          binaries.executable()
        }
      }
    }
  }

  if(ios) {
    ios()
  }
}

/**
 * Provides the existing [androidMain][KotlinSourceSet] element.
 */
val NamedDomainObjectContainer<KotlinSourceSet>.androidMain
  get() = named<KotlinSourceSet>("androidMain")

private val KotlinCompilationData<*>.libs get() = project.the<LibrariesForLibs>()
