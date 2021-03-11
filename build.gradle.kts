buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        jcenter()
    }

    dependencies {
        classpath(kotlin("gradle-plugin", version = "1.4.31"))
        classpath("com.android.tools.build:gradle:7.0.0-alpha09")
        classpath("org.jetbrains.compose:compose-gradle-plugin:0.3.2")
        classpath("com.vanniktech:gradle-maven-publish-plugin:0.14.2")
        classpath("org.jetbrains.dokka:dokka-gradle-plugin:1.4.20")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        jcenter()
    }
}
