package com.am.jarvis.core.model

/**
 * Represents smart device.
 *
 * @author Alex Mikhalochkin
 */
data class Device(
    val id: String,
    val room: Room,
    val name: DeviceName,
    val description: String,
    val groups: List<String>,
    val additionalData: Map<String, Any> = emptyMap(),
    // it's always MegaD for now, but should be changed in the future
    val sourceChannel: String = "MegaD",
    val type: DeviceType = DeviceType.ON_OFF,
    val technicalInfo: TechnicalInfo = TechnicalInfo()
)

data class Room(
    val primaryName: String,
    val additionalName: String = primaryName
)

enum class DeviceType {
    ON_OFF
}

data class DeviceName(
    val primaryName: String,
    val additionalName: String = primaryName
)
