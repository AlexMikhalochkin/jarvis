import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.2"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("jacoco")
    id("io.gitlab.arturbosch.detekt") version "1.18.0-RC2"
    kotlin("jvm") version "1.5.20"
    kotlin("plugin.spring") version "1.5.20"
    id("com.diffplug.spotless") version "5.14.2"
    id("org.openapi.generator") version "5.1.1"
}

group = "com.am"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":momomo-megad-connector"))
    implementation("io.github.microutils:kotlin-logging:2.1.21")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("javax.validation:validation-api:2.0.1.Final")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-web")

    testImplementation("commons-io:commons-io:2.11.0")
    testImplementation("com.squareup.okhttp3:okhttp:4.9.3")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.9.3")
    testImplementation("io.mockk:mockk:1.12.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.19.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}
tasks.withType<Test> {
    useJUnitPlatform()
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
    toolVersion = "0.8.7"
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
        csv.required.set(false)
        xml.outputLocation.set(file("$buildDir/reports/jacoco/report.xml"))
        html.outputLocation.set(layout.buildDirectory.dir("reports/jacoco"))
    }
}

tasks.withType<JacocoReport> {
    afterEvaluate {
        classDirectories.setFrom(files(classDirectories.files.map {
            fileTree(it).apply {
                exclude(
                    "com/am/jarvis/controller/generated/**",
                    "com/am/jarvis/controller/StubLoginController**"
                )
            }
        }))
    }
}

detekt {
    toolVersion = "1.18.0-RC2"
    config = files("configuration/detekt/detekt.yml")
    buildUponDefaultConfig = true
    reports {
        html {
            enabled = true
            destination = file("$buildDir/reports/detekt/report.html")
        }
    }
}

configure<com.diffplug.gradle.spotless.SpotlessExtension> {
    kotlin {
        target("**/generated/*/*.kt")
        ktfmt().dropboxStyle()
        ktlint()
        //diktat()   // has its own section below
        //prettier() // has its own section below
        //licenseHeader '/* (C)$YEAR */' // or licenseHeaderFile
    }
//    kotlinGradle {
//        //target '*.gradle.kts' // default target for kotlinGradle
//        ktlint() // or ktfmt() or prettier()
//    }
}

openApiGenerate {
    generatorName.set("kotlin-spring")
    inputSpec.set("$rootDir/configuration/codegenerator/jarvis.yaml")
    outputDir.set("$rootDir")
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

tasks.openApiGenerate { finalizedBy(tasks.spotlessApply) }
