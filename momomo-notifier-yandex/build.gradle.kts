plugins {
    id("OpenApiGeneratorPlugin")
}

tasks.getByName("bootJar") {
    enabled = false
}

openApiGenerate {
    inputSpec.set("$rootDir/configuration/codegenerator/yandex.yaml")
    globalProperties.set(
        mapOf(
            "modelDocs" to "false",
            "models" to "",
            "apis" to "false",
            "supportingFiles" to "false"
        )
    )
}

dependencies {
    implementation(project(":momomo-core"))

    implementation("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")
    implementation("javax.validation:validation-api:$validationApiVersion")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
}
