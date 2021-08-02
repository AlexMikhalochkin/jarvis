package com.mega.demo.model

/**
 * Represents smart home device.
 *
 * @author Alex Mikhalochkin
 */
data class Device(
    val externalDeviceId: String,
    val deviceCookie: Map<String, String>,
    val friendlyName: String,
    val manufacturerInfo: Map<String, String>,
    val deviceContext: Map<String, Any>,
    val deviceHandlerType: String,
    val deviceUniqueId: String
)
