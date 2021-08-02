package com.mega.demo.controller.model.smartthings

/**
 * Represents SmartThings request's headers.
 *
 * @author Alex Mikhalochkin
 */
data class Headers(
    val interactionType: String,
    val requestId: String,
    val schema: String = "st-schema",
    val version: String = "1.0"
)
