import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    kotlin("jvm")
    id("com.diffplug.spotless")
    id("jacoco")
    id("io.gitlab.arturbosch.detekt")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

group = "com.am"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

tasks.bootJar {
    enabled = false
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.springframework.boot:spring-boot-starter")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "11"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "11"
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
    config = files("../configuration/detekt/detekt.yml")
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
