package com.mega.demo.controller.model.smartthings

data class Command(
    val component: String,
    val capability: String,
    val command: String,
    val arguments: List<Any> = emptyList()
)
