package com.am.jarvis.core.model

/**
 * Represents technical information about a [Device].
 *
 * @author Alex Mikhalochkin
 */
data class TechnicalInfo(
    val manufacturer: String = "Provider2",
    val model: String = "hue g11",
    val hardwareVersion: String = "1.0",
    val softwareVersion: String = "1.0"
)
