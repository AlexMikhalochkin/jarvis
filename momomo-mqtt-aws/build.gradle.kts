tasks.getByName("bootJar") {
    enabled = false
}

dependencies {
    implementation(project(":momomo-core"))

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonModuleKotlinVersion")
    implementation("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("software.amazon.awssdk.iotdevicesdk:aws-iot-device-sdk:1.21.0")
}
