import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm")
    kotlin("plugin.spring") version kotlinVersion
    id("io.gitlab.arturbosch.detekt") version detektFormattingVersion
    id("io.spring.dependency-management") version springDependencyManagementVersion
    id("jacoco")
    id("org.springframework.boot") version springBootVersion
    id("org.sonarqube") version sonarqubeVersion
}

allprojects {
    group = "com.am"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

java.sourceCompatibility = JavaVersion.VERSION_21

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
        jvmTarget.set(JvmTarget.JVM_21)
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
        plugin("io.gitlab.arturbosch.detekt")
        plugin("io.spring.dependency-management")
        plugin("jacoco")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.jetbrains.kotlin.plugin.spring")
        plugin("org.springframework.boot")
    }

    repositories {
        mavenCentral()
    }

    dependencies {
        testImplementation("io.mockk:mockk:$mockkVersion")
        testImplementation("org.springframework.boot:spring-boot-starter-test")

        testRuntimeOnly("org.junit.platform:junit-platform-launcher")

        detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:$detektFormattingVersion")
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
        toolVersion = detektFormattingVersion
        config.setFrom("$rootDir/configuration/detekt/detekt.yml")
        buildUponDefaultConfig = true
    }

    jacoco {
        toolVersion = jacocoVersion
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
