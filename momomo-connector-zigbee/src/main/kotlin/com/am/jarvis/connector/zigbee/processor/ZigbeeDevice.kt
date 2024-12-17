package com.am.jarvis.connector.zigbee.processor

data class ZigbeeDevice(
    val action: String?,
    val humidity: Int?,
    val temperature: Int?,
    val battery: Int?,
    val linkquality: Int?,
    val voltage: Int?
)
