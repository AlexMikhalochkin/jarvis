package com.am.jarvis.connector.zigbee.processor

data class ZigbeeDevice(
    val action: String,
    val battery: Int,
    val linkquality: Int,
    val voltage: Int
)
