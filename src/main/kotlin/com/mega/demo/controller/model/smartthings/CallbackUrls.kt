package com.mega.demo.controller.model.smartthings

/**
 * Represents callback URLs.
 *
 * @author Alex Mikhalochkin
 */
data class CallbackUrls(
    val oauthToken: String,
    val stateCallback: String
)
