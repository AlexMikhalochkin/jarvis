package com.am.momomo.model

/**
 * Represents state of the [Device].
 *
 * @author Alex Mikhalochkin
 */
data class DeviceState(
    val deviceId: String,
    val isOn: Boolean,
    val customData: Map<String, Any> = emptyMap(),
    val stringState: String = if (isOn) "on" else "off",
    val intState: Int = if (isOn) 1 else 0
)
