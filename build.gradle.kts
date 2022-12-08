plugins {
    kotlin("jvm") version "1.7.21"
    id("org.jetbrains.kotlinx.benchmark") version("0.4.4")
}

repositories {
    mavenCentral()
}

tasks {
    sourceSets {
        main {
            java.srcDirs("src")
        }
    }

    wrapper {
        gradleVersion = "7.5.1"
    }
}

