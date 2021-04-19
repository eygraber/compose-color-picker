buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    dependencies {
        classpath(kotlin("gradle-plugin", version = "1.4.32"))
        classpath("com.android.tools.build:gradle:7.0.0-alpha14")
        classpath("org.jetbrains.compose:compose-gradle-plugin:0.4.0-build183")
        classpath("com.vanniktech:gradle-maven-publish-plugin:0.14.2")
        classpath("org.jetbrains.dokka:dokka-gradle-plugin:1.4.20")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
