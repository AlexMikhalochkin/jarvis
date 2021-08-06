package com.mega.demo.controller.model.smartthings

/**
 * Represents state of the device.
 * @see <a href="https://smartthings.developer.samsung.com/
 * docs/devices/smartthings-schema/smartthings-schema-reference.html#State-Refresh">State Refresh</a>
 *
 * @author Alex Mikhalochkin
 */
data class State(
    val capability: String,
    val attribute: String,
    val value: Any,
    val component: String = "main"
)
