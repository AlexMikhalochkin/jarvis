import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.diffplug.spotless")
    id("org.openapi.generator")
    kotlin("jvm")
}

sourceSets.getByName("main")
    .java
    .srcDirs(layout.buildDirectory.dir("generated/src/main/kotlin"))

gradle.projectsEvaluated {
    tasks.withType<KotlinCompile> {
        dependsOn(tasks.openApiGenerate)
    }
}

openApiGenerate {
    generatorName.set("kotlin-spring")
    templateDir.set("$rootDir/configuration/codegenerator/templates")
    configFile.set("$rootDir/configuration/codegenerator/config.json")
    outputDir.set(layout.buildDirectory.dir("generated").get().asFile.path)
    globalProperties.set(
        mapOf(
            "modelDocs" to "false",
            "models" to "",
            "apis" to "",
            "supportingFiles" to "false"
        )
    )
}

configure<com.diffplug.gradle.spotless.SpotlessExtension> {
    kotlin {
        target("build/generated/**/*.kt")
        ktfmt().dropboxStyle()
        ktlint()
    }
}

tasks.named("spotlessKotlin") {
    dependsOn(tasks.named("openApiGenerate"))
}

tasks.openApiGenerate { finalizedBy(tasks.spotlessApply) }
