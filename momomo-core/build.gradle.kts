plugins {
    kotlin("jvm")
}

tasks.getByName("bootJar") {
    enabled = false
}
