/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java application project to get you started.
 * For more details on building Java & JVM projects, please refer to https://docs.gradle.org/8.2.1/userguide/building_java_projects.html in the Gradle documentation.
 */

val classePrincipalServidor = "servidor.controlador.Servidor"
val classePrincipalCliente = "cliente.controlador.Cliente"

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
	mainClass.set(classePrincipalCliente)
}

tasks.register<JavaExec>("executarServidor") {
	classpath = sourceSets["main"].runtimeClasspath
	mainClass.set(classePrincipalServidor)
}

tasks.register<JavaExec>("executarCliente1") {
	classpath = sourceSets["main"].runtimeClasspath
	mainClass.set(classePrincipalCliente)
}

tasks.register<JavaExec>("executarCliente2") {
	classpath = sourceSets["main"].runtimeClasspath
	mainClass.set(classePrincipalCliente)
}

tasks {
	val jarServidor by creating(Jar::class) {
		archiveBaseName.set("servidor")
		manifest {
			attributes["Main-Class"] = classePrincipalServidor
		}
		from(sourceSets.main.get().output)
	}

	val jarCliente by creating(Jar::class) {
		archiveBaseName.set("cliente")
		manifest {
			attributes["Main-Class"] = classePrincipalCliente
		}
		from(sourceSets.main.get().output)
	}
}
