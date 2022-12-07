import com.eygraber.colorpicker.gradle.android
import com.eygraber.colorpicker.gradle.implementation
import org.gradle.accessors.dm.LibrariesForLibs
import com.android.build.gradle.BasePlugin as AndroidBasePlugin

val libs = the<LibrariesForLibs>()

plugins.withType<AndroidBasePlugin> {
  android {
    @Suppress("UnstableApiUsage")
    composeOptions.kotlinCompilerExtensionVersion = libs.versions.composeAndroid.compiler.get()

    dependencies {
      implementation(platform("androidx.compose:compose-bom:${libs.versions.composeAndroid.bom.get()}"))
    }
  }
}
