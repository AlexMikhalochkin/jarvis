package com.mega.demo.controller.model.smartthings

data class State(
    val component: String,
    val capability: String,
    val attribute: String,
    val value: Any
)
