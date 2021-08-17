package com.mega.demo.model

import com.mega.demo.model.yandex.Capability

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
    val capabilities: List<Capability>,
    val categories: List<String>,
    val groups: List<String>
)
