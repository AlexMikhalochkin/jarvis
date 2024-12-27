import org.gradle.kotlin.dsl.invoke

plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    implementation(project(":momomo-core"))
    implementation(project(":momomo-connector-megad"))
    implementation(project(":momomo-mqtt-core"))
    implementation(project(":momomo-connector-yandex"))
    implementation(project(":momomo-notifier-yandex"))
    implementation(project(":momomo-connector-smart-things"))
    implementation(project(":momomo-notifier-smart-things"))
    implementation(project(":momomo-connector-zigbee"))
    implementation(project(":momomo-mqtt-aws"))

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonModuleKotlinVersion")
    implementation("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    testImplementation("commons-io:commons-io:$commonsIoVersion")
    testImplementation("com.squareup.okhttp3:mockwebserver:$okhttpVersion")
    testImplementation("com.squareup.okhttp3:okhttp:$okhttpVersion")
    testImplementation("org.eclipse.paho:org.eclipse.paho.client.mqttv3:$pahoClientMqttVersion")
    testImplementation("org.mockito.kotlin:mockito-kotlin:$mockitoKotlinVersion")
}

val integrationTest: SourceSet by sourceSets.creating {
    kotlin.srcDirs("src/testIntegration/kotlin")
    resources.srcDirs("src/testIntegration/resources")

    compileClasspath += sourceSets.main.get().output
    runtimeClasspath += sourceSets.main.get().output
}

val integrationTestImplementation: Configuration by configurations.getting {
    extendsFrom(configurations["implementation"])
}

configurations[integrationTest.implementationConfigurationName].extendsFrom(configurations.testImplementation.get())
configurations[integrationTest.runtimeOnlyConfigurationName].extendsFrom(configurations.testRuntimeOnly.get())

val integrationTestTask = tasks.register<Test>("integrationTest") {
    description = "Runs integration tests."
    group = "verification"
    useJUnitPlatform()

    testClassesDirs = integrationTest.output.classesDirs
    classpath = sourceSets["integrationTest"].runtimeClasspath

    shouldRunAfter(tasks.test)
}

tasks.check {
    dependsOn(integrationTestTask)
}
