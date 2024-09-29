import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinLoggingVersion = "2.1.21"
val validationApiVersion = "2.0.1.Final"

plugins {
    id("OpenApiGeneratorPlugin")
}

tasks.getByName("bootJar") {
    enabled = false
}

sourceSets.getByName("main")
    .java
    .srcDirs(layout.buildDirectory.dir("generated/src/main/kotlin"))

gradle.projectsEvaluated {
    tasks.withType<KotlinCompile> {
        dependsOn(tasks.openApiGenerate)
    }
}

dependencies {
    implementation(project(":momomo-core"))

    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")
    implementation("javax.validation:validation-api:$validationApiVersion")
}
