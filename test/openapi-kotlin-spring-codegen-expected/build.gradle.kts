import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("com.codebootup.kotlin") version "1.0.1"
    id("org.jetbrains.dokka") version "1.8.10"
    jacoco
    id("com.diffplug.spotless") version "6.18.0"
    id("org.springframework.boot") version "3.0.2"
    id("io.spring.dependency-management") version "1.1.0"
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-test")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.springframework.boot:spring-boot-starter-security")
}

repositories {
    mavenCentral()
}

val bootJar : BootJar by tasks
bootJar.enabled = false