package com.mega.demo.model.yandex

/**
 * Represents Yandex device.
 *
 * @author Alex Mikhalochkin
 */
data class DeviceStateHolder(
    val id: String,
    val name: String? = null,
    val description: String? = null,
    val room: String? = null,
    val type: String? = null,
    val customData: Map<String, Any>? = null,
    val capabilities: List<Capability>? = null,
    val deviceInfo: DeviceInfo? = null
)
