package com.am.jarvis.model

/**
 * Represents technical information about a [Device].
 *
 * @author Alex Mikhalochkin
 */
data class TechnicalInfo(
    val manufacturer: String,
    val model: String,
    val hardwareVersion: String,
    val softwareVersion: String
)
