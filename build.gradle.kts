import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.2"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("jacoco")
    id("io.gitlab.arturbosch.detekt").version("1.18.0-RC2")
    kotlin("jvm") version "1.5.20"
    kotlin("plugin.spring") version "1.5.20"
    id("com.diffplug.spotless") version "5.14.2"
    id("org.openapi.generator") version "5.1.1"
}

group = "com.mega"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

subprojects {
    repositories {
        mavenCentral()
    }

    apply {
        plugin("io.spring.dependency-management")
    }
}

allprojects {
    repositories {
        mavenCentral()
    }

    apply {
        plugin("io.spring.dependency-management")
        plugin("org.jetbrains.kotlin.jvm")
    }

    dependencies {
        implementation("io.github.microutils:kotlin-logging:1.12.5")

        testImplementation("org.junit.jupiter:junit-jupiter:5.7.0")
    }
}

dependencies {
    implementation(project(":jarvis-integration"))
    implementation(project(":jarvis-rest"))
    implementation("org.springframework.boot:spring-boot-starter-web")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("commons-io:commons-io:2.11.0")

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.18.0-RC2")
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

detekt {
    toolVersion = "1.18.0-RC2"
    config = files("config/detekt/detekt.yml")
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
        ktfmt().dropboxStyle()    // has its own section below
        ktlint()   // has its own section below
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
    inputSpec.set("$rootDir/codegenerator/jarvis.yaml")
    outputDir.set("$rootDir")
    templateDir.set("$rootDir/codegenerator/templates")
    globalProperties.set(
        mapOf(
            "modelDocs" to "false",
            "models" to "",
            "apis" to "",
            "supportingFiles" to "false"
        )
    )
    configFile.set("$rootDir/codegenerator/config.json")
}

tasks.openApiGenerate { finalizedBy(tasks.spotlessApply) }