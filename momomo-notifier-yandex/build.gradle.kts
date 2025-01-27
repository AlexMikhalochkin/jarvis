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

    implementation("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")
    implementation("javax.validation:validation-api:$validationApiVersion")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
}
