val kotlinLoggingVersion = "2.1.21"
val validationApiVersion = "2.0.1.Final"

plugins {
    id("OpenApiGeneratorPlugin")
}

tasks.getByName("bootJar") {
    enabled = false
}

openApiGenerate {
    inputSpec.set("$rootDir/configuration/codegenerator/yandex.yaml")
}

dependencies {
    implementation(project(":momomo-core"))

    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")
    implementation("javax.validation:validation-api:$validationApiVersion")
}
