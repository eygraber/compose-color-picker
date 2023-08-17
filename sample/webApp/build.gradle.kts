import org.jetbrains.kotlin.gradle.targets.js.ir.DefaultIncrementalSyncTask
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension

plugins {
  id("com.eygraber.conventions-kotlin-multiplatform")
  id("com.eygraber.conventions-detekt")
  id("com.eygraber.conventions-compose-jetbrains")
}

val copyJsResources = tasks.create("copyJsResourcesWorkaround", Copy::class.java) {
  from(project(":sample").file("src/commonMain/resources"))
  into("build/processedResources/js/main")
}

val copyWasmResources = tasks.create("copyWasmResourcesWorkaround", Copy::class.java) {
  from(project(":sample").file("src/commonMain/resources"))
  into("build/processedResources/wasmJs/main")
}

afterEvaluate {
  tasks.withType(DefaultIncrementalSyncTask::class.java).configureEach {
    dependsOn(copyWasmResources)
  }

  project.tasks.getByName("jsProcessResources").finalizedBy(copyJsResources)
  project.tasks.getByName("wasmJsProcessResources").finalizedBy(copyWasmResources)
}

kotlin {
  kmpTargets(
    project = project,
    js = true,
    binaryType = BinaryType.Executable,
    jsModuleName = "compose-color-picker",
    wasmJs = true,
    wasmJsModuleName = "compose-color-picker"
  )

  sourceSets {
    getByName("jsWasmMain") {
      dependencies {
        implementation(compose.foundation)
        implementation(compose.material3)

        implementation(projects.sample.shared)
      }
    }
  }
}

compose.experimental {
  web.application {}
}
