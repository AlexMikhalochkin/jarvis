package com.mega.demo.controller.model.smartthings

data class DeviceState(
    val externalDeviceId: String,
    val states: List<State>,
    val deviceCookie: Map<String, String> = emptyMap()
)
