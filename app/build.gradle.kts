/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java application project to get you started.
 * For more details on building Java & JVM projects, please refer to https://docs.gradle.org/8.2.1/userguide/building_java_projects.html in the Gradle documentation.
 */

plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // Use JUnit test framework.
    testImplementation("junit:junit:4.13.2")

    // This dependency is used by the application.
    implementation("com.google.guava:guava:31.1-jre")
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

application {
    // Define the main class for the application.
    mainClass.set("io.github.henriquemcc.chat.java.ClientMain")
}

tasks.register<JavaExec>("runServer") {
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("servidor.controlador.Servidor")
}

tasks.register<JavaExec>("runClient1") {
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("cliente.controlador.Cliente")
}

tasks.register<JavaExec>("runClient2") {
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("cliente.controlador.Cliente")
}