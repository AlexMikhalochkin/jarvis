package com.mega.demo.model

/**
 * Represents state of the [Device].
 *
 * @author Alex Mikhalochkin
 */
data class DeviceState(
    val deviceId: String,
    val isOn: Boolean? = null
)
