import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

val group = "fidesdigital"
val version = "0.0.1"

plugins {
    val kotlinVersion = "1.3.21"
    val springBootVersion = "2.2.0.BUILD-SNAPSHOT"

    id("org.jetbrains.kotlin.jvm") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion
    id("io.spring.dependency-management") version "1.0.6.RELEASE"
    id("org.springframework.boot") version springBootVersion
}

repositories {
    mavenCentral()
    maven("https://repo.spring.io/snapshot")
    maven("https://repo.spring.io/milestone")
}

val compileKotlin by tasks.getting(KotlinCompile::class) {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xjsr305=strict")
    }
}

val compileTestKotlin by tasks.getting(KotlinCompile::class) {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xjsr305=strict")

    }
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("org.springframework.boot:spring-boot-starter-webflux")

    /* Persistence */
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
}