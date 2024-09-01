package com.am.momomo.model

/**
 * Represents state of the [Device].
 *
 * @author Alex Mikhalochkin
 */
data class DeviceState(
    val deviceId: String?,
    val isOn: Boolean?,
    val customData: Map<String, Any> = emptyMap()
)
