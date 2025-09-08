import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Latest Supports with my IntelliJ version
// JDK 20
// Gradle Not sure, currently using 8.3
// Kotlin

plugins {
    kotlin("jvm") version "1.6.10"
    id("org.jetbrains.dokka") version "1.9.10"
    application
}

group = "me.joaquin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(kotlin("stdlib"))
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("MainKt")
}