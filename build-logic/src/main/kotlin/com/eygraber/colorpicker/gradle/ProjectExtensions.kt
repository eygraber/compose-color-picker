@file:Suppress("NOTHING_TO_INLINE")

package com.eygraber.colorpicker.gradle

import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.add
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

internal inline fun Project.android(action: Action<BaseExtension>) {
  action.execute(extensions.getByType(BaseExtension::class.java))
}

internal inline fun Project.androidLibrary(action: Action<LibraryExtension>) {
  action.execute(extensions.getByType(LibraryExtension::class.java))
}

internal inline fun Project.kotlin(configure: Action<KotlinProjectExtension>) =
  (this as ExtensionAware).extensions.configure("kotlin", configure)

/**
 * Adds a dependency to the 'implementation' configuration.
 *
 * @param dependencyNotation notation for the dependency to be added.
 * @return The dependency.
 *
 * @see [DependencyHandler.add]
 */
internal fun DependencyHandler.implementation(dependencyNotation: Any): Dependency? =
  add("implementation", dependencyNotation)
