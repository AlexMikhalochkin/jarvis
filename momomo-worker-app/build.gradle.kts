plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    implementation(project(":momomo-core"))
    implementation(project(":momomo-connector-megad"))
    implementation(project(":momomo-connector-zigbee"))
    implementation(project(":momomo-mqtt-core"))
    implementation(project(":momomo-notifier-yandex"))

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonModuleKotlinVersion")
    implementation("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    testImplementation("commons-io:commons-io:$commonsIoVersion")
    testImplementation("com.squareup.okhttp3:mockwebserver:$okhttpVersion")
    testImplementation("com.squareup.okhttp3:okhttp:$okhttpVersion")
    testImplementation("org.eclipse.paho:org.eclipse.paho.client.mqttv3:$pahoClientMqttVersion")
    testImplementation("org.mockito.kotlin:mockito-kotlin:$mockitoKotlinVersion")
}
