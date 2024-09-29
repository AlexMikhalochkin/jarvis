import org.gradle.kotlin.dsl.invoke

plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
}

val kotlinLoggingVersion = "2.1.21"
val jacksonModuleKotlinVersion = "2.12.3"
val pahoClientMqttVersion = "1.2.5"
val commonsIoVersion = "2.11.0"
val okhttpVersion = "4.12.0"
val mockitoKotlinVersion = "4.0.0"
val detektFormattingVersion = "1.23.7"

dependencies {
    implementation(project(":momomo-core"))
    implementation(project(":momomo-connector-megad"))
    implementation(project(":momomo-connector-yandex"))
    implementation(project(":momomo-connector-smart-things"))
    implementation("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonModuleKotlinVersion")
    implementation("org.springframework.boot:spring-boot-starter-security")

    testImplementation("org.eclipse.paho:org.eclipse.paho.client.mqttv3:$pahoClientMqttVersion")
    testImplementation("commons-io:commons-io:$commonsIoVersion")
    testImplementation("com.squareup.okhttp3:okhttp:$okhttpVersion")
    testImplementation("com.squareup.okhttp3:mockwebserver:$okhttpVersion")
    testImplementation("org.mockito.kotlin:mockito-kotlin:$mockitoKotlinVersion")

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:$detektFormattingVersion")
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
