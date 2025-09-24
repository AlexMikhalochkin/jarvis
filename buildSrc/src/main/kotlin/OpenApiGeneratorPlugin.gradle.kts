import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    id("com.diffplug.spotless")
    id("org.openapi.generator")
    kotlin("jvm")
}

sourceSets.getByName("main")
    .java
    .srcDirs(layout.buildDirectory.dir("generated/src/main/kotlin"))

gradle.projectsEvaluated {
    tasks.withType<KotlinJvmCompile> {
        dependsOn(tasks.openApiGenerate)
    }
}

openApiGenerate {
    generatorName.set("kotlin-spring")
    templateDir.set("$rootDir/configuration/codegenerator/templates")
    configFile.set("$rootDir/configuration/codegenerator/config.json")
    outputDir.set(layout.buildDirectory.dir("generated").get().asFile.path)
    ignoreFileOverride.set("$projectDir/.openapi-generator-ignore")
}

configure<com.diffplug.gradle.spotless.SpotlessExtension> {
    kotlin {
        target("build/generated/**/*.kt")
        ktfmt().kotlinlangStyle()
        ktlint()
    }
}

tasks.named("spotlessKotlin") {
    dependsOn(tasks.named("openApiGenerate"))
}

tasks.openApiGenerate { finalizedBy(tasks.spotlessApply) }
