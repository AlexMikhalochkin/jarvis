package com.mega.demo.controller.model.smartthings

/**
 * Represents request from SmartThings.
 *
 * @author Alex Mikhalochkin
 */
data class SmartThingsRequest(
    val headers: Headers,
    val callbackAuthentication: CallbackAuthentication? = null,
    val devices: List<SmartThingsDevice>? = null,
    val deviceState: List<DeviceState>? = null,
    val authentication: Authentication? = null,
    val callbackUrls: CallbackUrls? = null
)
