package com.am.jarvis.model

/**
 * Represents state of the [Device].
 *
 * @author Alex Mikhalochkin
 */
data class DeviceState(val deviceId: String?, val port: Int?, val isOn: Boolean?)
