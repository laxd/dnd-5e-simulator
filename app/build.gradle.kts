
plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    kotlin("jvm") version "1.4.30"

    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3")
    implementation("org.springframework.boot:spring-boot-starter:2.4.0")
    implementation("com.beust:klaxon:5.5")
    implementation("org.slf4j:slf4j-api:1.7.30")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.4.32")

    testCompile("org.assertj:assertj-core:3.11.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.1")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    testImplementation("io.mockk:mockk:1.11.0")
    testCompile("org.junit.jupiter:junit-jupiter-params:5.7.0")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks {
    "test"(Test::class) {
        useJUnitPlatform()
    }
}

application {
    // Define the main class for the application.
    mainClass.set("uk.laxd.dndSimulator.MainKt")
}