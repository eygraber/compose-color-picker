import com.android.build.gradle.BasePlugin
import com.eygraber.colorpicker.gradle.android
import org.gradle.accessors.dm.LibrariesForLibs

val libs = the<LibrariesForLibs>()

plugins {
  id("org.jetbrains.compose")
  id("color-compose")
}

compose.kotlinCompilerPlugin.set(libs.composeAndroid.compiler.map { it.toString() })

plugins.withType<BasePlugin> {
  android {
    dependencies {
      // jetbrains compose plugin rewrites compose dependencies for android to point to androidx
      // if we want to use the compose BOM we need to rewrite the rewritten dependencies to not include a version
      components {
        all {
          val isCompiler = id.group.endsWith("compiler")
          val isCompose = id.group.startsWith("androidx.compose")
          val isBom = id.name == "compose-bom"

          val override = isCompose && !isCompiler && !isBom
          if(override) {
            // copied from Jetbrains Compose RedirectAndroidVariants - https://shorturl.at/dioY9
            listOf(
              "debugApiElements-published",
              "debugRuntimeElements-published",
              "releaseApiElements-published",
              "releaseRuntimeElements-published"
            ).forEach { variantNameToAlter ->
              withVariant(variantNameToAlter) {
                withDependencies {
                  removeAll { true } // remove androidx artifact with version
                  add("${id.group}:${id.name}") // add androidx artifact without version
                }
              }
            }
          }
        }
      }
    }
  }
}
