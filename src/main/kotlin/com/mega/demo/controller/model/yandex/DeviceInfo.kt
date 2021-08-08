package com.mega.demo.controller.model.yandex

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Represents device's additional information.
 *
 * @author Alex Mikhalochkin
 */
data class DeviceInfo(
    val manufacturer: String,
    val model: String,
    @JsonProperty("hw_version") val hwVersion: String,
    @JsonProperty("sw_version") val swVersion: String
)
