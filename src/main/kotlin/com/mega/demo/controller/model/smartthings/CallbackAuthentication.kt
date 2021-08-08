package com.mega.demo.controller.model.smartthings

/**
 * Represents callback authentication info.
 *
 * @author Alex Mikhalochkin
 */
data class CallbackAuthentication(
    val grantType: String?,
    val code: String?,
    val clientId: String?,
    val clientSecret: String?,
    val tokenType: String? = null,
    val accessToken: String? = null,
    val refreshToken: String? = null,
    val expiresIn: Int? = null
)
