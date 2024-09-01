package com.am.momomo.model

/**
 * Represents smart device.
 *
 * @author Alex Mikhalochkin
 */
data class Device(
    val id: String,
    val room: Room,
    val name: DeviceName,
    val technicalInfo: TechnicalInfo,
    val description: String,
    val capabilities: List<String>,
    val categories: List<String>,
    val groups: List<String>,
    val additionalData: Map<String, Any> = emptyMap(),
    val type: DeviceType = DeviceType.ON_OFF
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
