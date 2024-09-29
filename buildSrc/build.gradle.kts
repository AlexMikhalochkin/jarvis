import org.gradle.kotlin.dsl.`kotlin-dsl`

plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://plugins.gradle.org/m2/")
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation("org.openapitools:openapi-generator-gradle-plugin:5.1.1")
    implementation("com.diffplug.spotless:spotless-plugin-gradle:5.14.2")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.10")
}
