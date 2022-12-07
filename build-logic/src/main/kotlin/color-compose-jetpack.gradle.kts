import com.eygraber.colorpicker.gradle.android
import org.gradle.kotlin.dsl.withType
import com.android.build.gradle.BasePlugin as AndroidBasePlugin

plugins {
  id("color-compose")
}

plugins.withType<AndroidBasePlugin> {
  android {
    @Suppress("UnstableApiUsage")
    buildFeatures.compose = true
  }
}
