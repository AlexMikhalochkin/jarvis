plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "jarvis"

include("momomo-app")
include("momomo-worker-app")
include("momomo-core")
include("momomo-connector-megad")
include("momomo-connector-yandex")
include("momomo-connector-smart-things")
include("momomo-mqtt-core")
include("momomo-connector-zigbee")
include("momomo-mqtt-aws")
include("momomo-notifier-yandex")
include("momomo-notifier-smart-things")
