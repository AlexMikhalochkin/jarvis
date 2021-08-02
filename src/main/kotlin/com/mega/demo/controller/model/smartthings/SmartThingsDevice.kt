package com.mega.demo.controller.model.smartthings

/**
 * Represents SmartThings device.
 *
 * @author Alex Mikhalochkin
 */
data class SmartThingsDevice(
    val externalDeviceId: String,
    val deviceCookie: Map<String, String>? = null,
    val friendlyName: String? = null,
    val manufacturerInfo: Map<String, String>? = null,
    val deviceContext: Map<String, Any>? = null,
    val deviceHandlerType: String? = null,
    val deviceUniqueId: String? = null,
    val commands: List<Command>? = null
)
