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
}

repositories {
    mavenCentral()
}
