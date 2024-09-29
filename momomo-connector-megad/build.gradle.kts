val pahoClientMqttVersion = "1.2.5"
val jacksonModuleKotlinVersion = "2.12.3"
val kotlinLoggingVersion = "2.1.21"

tasks.getByName("bootJar") {
    enabled = false
}

dependencies {
    implementation(project(":momomo-core"))

    implementation("org.eclipse.paho:org.eclipse.paho.client.mqttv3:$pahoClientMqttVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonModuleKotlinVersion")
    implementation("org.springframework.boot:spring-boot-starter:3.3.3")
    implementation("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")
}
