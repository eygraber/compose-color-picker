plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

android {
    configurations {
        create("testApi")
        create("testDebugApi")
        create("testReleaseApi")
    }

    compileSdk = 30

    defaultConfig {
        minSdk = 24
        targetSdk = 30
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    packagingOptions {
        resources.pickFirsts += "META-INF/*"
    }

    dependencies {
        coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")
    }
}

kotlin {
    explicitApi()

    android {
        publishLibraryVariants("release")
    }

    jvm("desktop").compilations.all {
        compileKotlinTask.sourceCompatibility = "1.8"
        compileKotlinTask.targetCompatibility = "1.8"

        kotlinOptions {
            allWarningsAsErrors = true
            jvmTarget = "1.8"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.ui)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val androidMain by getting {
            dependsOn(commonMain)
        }

        val desktopMain by getting {
            dependsOn(commonMain)
        }

        all {
            languageSettings.useExperimentalAnnotation("kotlin.RequiresOptIn")
        }
    }
}

apply(from = File(rootDir, "publishing.gradle"))
