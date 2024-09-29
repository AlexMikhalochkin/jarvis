import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("io.spring.dependency-management") version "1.1.6"
    id("org.springframework.boot") version "3.3.3"
    id("jacoco")
    id("org.sonarqube") version "5.1.0.4882"
    id("io.gitlab.arturbosch.detekt") version "1.23.7"
    kotlin("jvm")
    kotlin("plugin.spring") version "2.0.10"
}

allprojects {
    group = "com.am"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

java.sourceCompatibility = JavaVersion.VERSION_17

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

tasks.getByName("bootJar") {
    enabled = false
}

tasks.getByName<Jar>("jar") {
    enabled = false
}

subprojects {
    apply {
        plugin("io.spring.dependency-management")
        plugin("jacoco")
        plugin("org.springframework.boot")
        plugin("io.gitlab.arturbosch.detekt")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.jetbrains.kotlin.plugin.spring")
    }

    repositories {
        mavenCentral()
    }

    val mockkVersion = "1.12.1"

    dependencies {
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("io.mockk:mockk:$mockkVersion")

        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        jvmArgs = listOf("-Xshare:off")
        finalizedBy(tasks.jacocoTestReport)
    }

    tasks.detekt.configure {
        reports {
            html {
                required.set(true)
                outputLocation.set(layout.buildDirectory.file("reports/detekt/report.html"))
            }
        }
    }

    detekt {
        toolVersion = "1.23.7"
        config.setFrom("$rootDir/configuration/detekt/detekt.yml")
        buildUponDefaultConfig = true
    }

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
        dependsOn(tasks.test)
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
}
