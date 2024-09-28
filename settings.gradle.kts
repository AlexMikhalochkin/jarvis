plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "jarvis"

include("momomo-app")
include("momomo-core")
include("momomo-connector-megad")
