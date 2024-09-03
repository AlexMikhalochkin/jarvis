import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.3.3"
    id("io.spring.dependency-management") version "1.1.6"
    id("jacoco")
    id("io.gitlab.arturbosch.detekt") version "1.23.6"
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.spring") version "1.9.23"
    id("com.diffplug.spotless") version "5.14.2"
    id("org.openapi.generator") version "5.1.1"
}

group = "com.am"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.microutils:kotlin-logging:2.1.21")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("javax.validation:validation-api:2.0.1.Final")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.3")

    implementation("org.eclipse.paho:org.eclipse.paho.client.mqttv3:1.2.5")
    testImplementation("commons-io:commons-io:2.11.0")
    testImplementation("com.squareup.okhttp3:okhttp:4.9.3")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.9.3")
    testImplementation("io.mockk:mockk:1.12.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.23.6")
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

sourceSets {
    create("testIntegration") {
        kotlin {
            compileClasspath += main.get().output + configurations.testRuntimeClasspath
            runtimeClasspath += output + compileClasspath
        }
    }
}

sourceSets.getByName("main")
    .java
    .srcDirs("${buildDir}/generated/src/main/kotlin")

val testIntegration = task<Test>("testIntegration") {
    description = "Runs the integration tests"
    group = "verification"
    testClassesDirs = sourceSets["testIntegration"].output.classesDirs
    classpath = sourceSets["testIntegration"].runtimeClasspath
    mustRunAfter(tasks["test"])
}

tasks.check {
    dependsOn(testIntegration)
}

jacoco {
    toolVersion = "0.8.8"
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
    toolVersion = "1.23.6"
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
