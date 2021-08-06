package com.mega.demo.controller.model.smartthings

/**
 * Represents command for the device.
 * @see <https://smartthings.developer.samsung.com/
 * docs/devices/smartthings-schema/smartthings-schema-reference.html#Command>Command</a>
 *
 * @author Alex Mikhalochkin
 */
data class Command(
    val capability: String,
    val command: String,
    val arguments: List<Any> = emptyList(),
    val component: String = "main"
)
