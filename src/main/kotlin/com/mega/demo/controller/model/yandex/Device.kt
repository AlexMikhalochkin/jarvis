package com.mega.demo.controller.model.yandex

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Represents Yandex device.
 *
 * @author Alex Mikhalochkin
 */
data class Device(
    val id: String,
    val name: String? = null,
    val description: String? = null,
    val room: String? = null,
    val type: String? = null,
    @JsonProperty("custom_data") val customData: Map<String, Any>? = null,
    val capabilities: List<Capability>? = null,
    @JsonProperty("device_info") val deviceInfo: DeviceInfo? = null
)
