package com.am.momomo.model

import org.springframework.boot.context.properties.ConstructorBinding

/**
 * Represents smart device.
 *
 * @author Alex Mikhalochkin
 */
data class Device @ConstructorBinding constructor(
    val id: String,
    val room: Room,
    val name: DeviceName,
    val description: String,
    val groups: List<String>,
    val additionalData: Map<String, Any> = emptyMap(),
    //it's always MegaD for now, but should be changed in the future
    val sourceChannel: String = "MegaD",
    val type: DeviceType = DeviceType.ON_OFF,
    val technicalInfo: TechnicalInfo = TechnicalInfo()
)

data class Room @ConstructorBinding constructor(
    val primaryName: String,
    val additionalName: String = primaryName
)

enum class DeviceType {
    ON_OFF
}

data class DeviceName @ConstructorBinding constructor(
    val primaryName: String,
    val additionalName: String = primaryName
)
