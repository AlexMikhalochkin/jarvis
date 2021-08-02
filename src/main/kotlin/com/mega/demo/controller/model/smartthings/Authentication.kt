package com.mega.demo.controller.model.smartthings

/**
 * Represents authentication info.
 *
 * @author Alex Mikhalochkin
 */
data class Authentication(
    val token: String,
    val tokenType: String = "Bearer"
)
