package com.mega.demo.model

/**
 * Represents status of the [Device].
 *
 * @author Alex Mikhalochkin
 */
data class DeviceStatus(
    val externalDeviceId: String,
    val status: Boolean
)
