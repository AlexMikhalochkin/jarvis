package com.mega.demo.model

/**
 * Represents smart device.
 *
 * @author Alex Mikhalochkin
 */
data class Device(
    val id: String,
    val room: Map<Provider, String>,
    val type: Map<Provider, String>,
    val name: Map<Provider, String>,
    val technicalInfo: TechnicalInfo,
    val customData: Map<String, Any>,
    val description: String,
    val capabilities: List<String>,
    val categories: List<String>,
    val groups: List<String>
)
