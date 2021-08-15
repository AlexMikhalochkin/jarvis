plugins {
    id("org.openapi.generator") version "5.1.1"
    id("com.diffplug.spotless") version "5.14.2"
}

repositories {
    mavenCentral()
}

openApiGenerate {
    generatorName.set("kotlin-spring")
    inputSpec.set("$rootDir/jarvis.yaml")
    outputDir.set("$rootDir/..")
    templateDir.set("$rootDir/templates")
    globalProperties.set(
        mapOf(
            "modelDocs" to "false",
            "models" to "",
            "apis" to "",
            "supportingFiles" to "false"
        )
    )
    configFile.set("$rootDir/config.json".toString())
}
