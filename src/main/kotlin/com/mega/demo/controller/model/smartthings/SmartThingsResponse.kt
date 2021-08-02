package com.mega.demo.controller.model.smartthings

/**
 * Represents response for SmartThings.
 *
 * @author Alex Mikhalochkin
 */
data class SmartThingsResponse(
    val headers: Headers,
    val requestGrantCallbackAccess: Boolean? = null,
    val devices: List<SmartThingsDevice?>? = null,
    val deviceState: List<DeviceState?>? = null
)
