package com.mega.demo.controller.model.smartthings

/**
 * Represents request from SmartThings.
 *
 * @author Alex Mikhalochkin
 */
data class SmartThingsRequest(
    val headers: Headers,
    val authentication: Authentication,
    val devices: List<SmartThingsDevice>?
)
