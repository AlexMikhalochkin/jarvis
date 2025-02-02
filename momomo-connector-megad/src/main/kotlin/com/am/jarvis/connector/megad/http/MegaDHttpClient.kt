package com.am.jarvis.connector.megad.http

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

/**
 * This class is responsible for making HTTP requests to the MegaD PCL.
 *
 * @author Alex Mikhalochkin
 */
@Component
class MegaDHttpClient(
    @Value("\${megad.url}") private val baseUrl: String,
    private val webClient: WebClient
) {

    /**
     * This method is responsible for getting the states of all MegaD ports.
     *
     * @return The states of all ports as a semicolon separated string.
     * for example: 0/3497;0/8;0/588;00;0;0;0;0;1;0;0;0/0;0/1016;0/0;0/559;0;0;1;1;1;0;1
     */
    fun getStatesForAllPorts(): String {
        webClient.get()
            .uri(baseUrl, mapOf("cmd" to "all", "f" to "d"))
            .retrieve()
            .bodyToMono(String::class.java)
            .block()
            .let { return it ?: "" }
    }
}
