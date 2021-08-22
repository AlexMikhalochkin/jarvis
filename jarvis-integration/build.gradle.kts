plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("jacoco")
    id("io.gitlab.arturbosch.detekt")
    kotlin("jvm")
    kotlin("plugin.spring")
    id("com.diffplug.spotless")
    id("org.openapi.generator")
}

tasks.bootJar{
    enabled = false
}

tasks.jar{
    enabled = true
}

dependencies{
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-activemq")
    implementation("org.apache.activemq:activemq-broker")
    implementation("org.apache.activemq:activemq-kahadb-store")

    testImplementation("com.squareup.okhttp3:okhttp:4.0.1")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.0.1")
}
