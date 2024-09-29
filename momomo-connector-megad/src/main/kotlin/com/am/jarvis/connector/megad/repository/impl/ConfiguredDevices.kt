package com.am.jarvis.connector.megad.repository.impl

import com.am.jarvis.core.model.Device
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 * Represents devices configured via application properties for in memory repository.
 *
 * @author Alex Mikhalochkin
 */
@Component
@ConfigurationProperties(prefix = "configured-devices")
data class ConfiguredDevices(
    val devices: List<Device>,
)
