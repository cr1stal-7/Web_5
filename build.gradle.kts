buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath ("com.android.tools.build:gradle:7.2.1")
        // Другие classpath зависимости
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}


plugins {
    kotlin("jvm") version "1.8.21" apply false
    id("com.android.application") version "8.1.0" apply false
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}