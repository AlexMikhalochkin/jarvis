tasks.getByName("bootJar") {
    enabled = false
}

dependencies {
    implementation(project(":momomo-core"))

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonModuleKotlinVersion")
    implementation("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")
    implementation("org.eclipse.paho:org.eclipse.paho.client.mqttv3:$pahoClientMqttVersion")
    implementation("org.springframework.boot:spring-boot-starter")
}
