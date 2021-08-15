package com.mega.demo.model.yandex

/**
 * Represents device's additional information.
 *
 * @author Alex Mikhalochkin
 */
data class DeviceInfo(
    val manufacturer: String,
    val model: String,
    val hwVersion: String,
    val swVersion: String
)
