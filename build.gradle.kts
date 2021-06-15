plugins {
    kotlin("jvm") version "1.5.0"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.5.0"
    id("com.diffplug.spotless") version "5.12.5"
    `maven-publish`
}

group = "com.github.Starlight220"
check(project.name == "ActionsKtLib")
version = "0.1"

repositories {
    mavenLocal()
    mavenCentral()
}

java {
    withSourcesJar()
    withJavadocJar()
}

spotless {
    kotlin {
        ktfmt("0.25").kotlinlangStyle()
        endWithNewline()
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()

            from(components["java"])
        }
    }
}

kotlin.explicitApi()

dependencies {
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.1")
}