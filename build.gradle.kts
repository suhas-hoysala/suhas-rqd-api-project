plugins {
    id("org.springframework.boot") version "3.0.0"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    kotlin("jvm") version "1.8.20"
    kotlin("plugin.spring") version "1.8.20"
    application
}

group = "com.suhashoysala.api"
version = "1.0-SNAPSHOT"

java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("commons-cli:commons-cli:1.4")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

application {
    mainClass.set("com.suhashoysala.api.MessageProcessingApp")
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    archiveBaseName.set("message-processing-server")
    archiveVersion.set("")
}

tasks.register("install") {
    dependsOn("bootJar")
    doLast {
        println("Jar created successfully: build/libs/message-processing-server.jar")
    }
}

tasks.named<JavaExec>("run") {
    doFirst {
        val serverAddress = (project.findProperty("serverAddress") as? String) ?: "localhost"
        val serverPort = (project.findProperty("serverPort") as? String) ?: "8080"

        args = listOf(
            "--serverAddress", serverAddress,
            "--serverPort", serverPort
        )
    }

    mainClass.set("com.suhashoysala.api.MessageProcessingApp")
}