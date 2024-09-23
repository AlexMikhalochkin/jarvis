import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.3.3"
    id("io.spring.dependency-management") version "1.1.6"
    id("jacoco")
    id("org.sonarqube") version "5.1.0.4882"
    id("io.gitlab.arturbosch.detekt") version "1.23.7"
    kotlin("jvm") version "2.0.10"
    kotlin("plugin.spring") version "2.0.10"
    id("com.diffplug.spotless") version "5.14.2"
    id("org.openapi.generator") version "5.1.1"
}

group = "com.am"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

val kotlinLoggingVersion = "2.1.21"
val validationApiVersion = "2.0.1.Final"
val jacksonModuleKotlinVersion = "2.12.3"
val pahoClientMqttVersion = "1.2.5"
val commonsIoVersion = "2.11.0"
val okhttpVersion = "4.12.0"
val mockkVersion = "1.12.1"
val mockitoKotlinVersion = "4.0.0"
val detektFormattingVersion = "1.23.7"

dependencies {
    implementation("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("javax.validation:validation-api:$validationApiVersion")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonModuleKotlinVersion")
    implementation("org.springframework.boot:spring-boot-starter-security")

    implementation("org.eclipse.paho:org.eclipse.paho.client.mqttv3:$pahoClientMqttVersion")
    testImplementation("commons-io:commons-io:$commonsIoVersion")
    testImplementation("com.squareup.okhttp3:okhttp:$okhttpVersion")
    testImplementation("com.squareup.okhttp3:mockwebserver:$okhttpVersion")
    testImplementation("io.mockk:mockk:$mockkVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.mockito.kotlin:mockito-kotlin:$mockitoKotlinVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:$detektFormattingVersion")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    jvmArgs = listOf("-Xshare:off")
}
tasks.test {
    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}
tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
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

sourceSets.getByName("main")
    .java
    .srcDirs(layout.buildDirectory.dir("generated/src/main/kotlin"))

jacoco {
    toolVersion = "0.8.12"
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
        csv.required.set(false)
        xml.outputLocation.set(layout.buildDirectory.file("reports/jacoco/report.xml"))
        html.outputLocation.set(layout.buildDirectory.dir("reports/jacoco"))
    }
}

tasks.withType<JacocoReport> {
    afterEvaluate {
        classDirectories.setFrom(files(classDirectories.files.map {
            fileTree(it).apply {
                exclude(
                    "**/*\$logger\$*.class",
                    "com/am/jarvis/controller/generated/**",
                    "com/am/jarvis/StubLoginController**"
                )
            }
        }))
    }
}

detekt {
    toolVersion = "1.23.7"
    config.setFrom("configuration/detekt/detekt.yml")
    buildUponDefaultConfig = true
}

tasks.detekt.configure {
    reports {
        html {
            required.set(true)
            outputLocation.set(layout.buildDirectory.file("reports/detekt/report.html"))
        }
    }
}

configure<com.diffplug.gradle.spotless.SpotlessExtension> {
    kotlin {
        target("build/generated/**/*.kt")
        ktfmt().dropboxStyle()
        ktlint()
    }
}

openApiGenerate {
    generatorName.set("kotlin-spring")
    inputSpec.set("$rootDir/configuration/codegenerator/jarvis.yaml")
    outputDir.set("$rootDir/build/generated")
    templateDir.set("$rootDir/configuration/codegenerator/templates")
    globalProperties.set(
        mapOf(
            "modelDocs" to "false",
            "models" to "",
            "apis" to "",
            "supportingFiles" to "false"
        )
    )
    configFile.set("$rootDir/configuration/codegenerator/config.json")
}

tasks.named("spotlessKotlin") {
    dependsOn(tasks.named("openApiGenerate"))
}
tasks.openApiGenerate { finalizedBy(tasks.spotlessApply) }

//tasks.prepareKotlinBuildScriptModel {
//    finalizedBy(tasks.openApiGenerate)
//}

gradle.projectsEvaluated {
    tasks.withType<KotlinCompile> {
        dependsOn(tasks.openApiGenerate)
    }
}
