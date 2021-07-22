package com.mega.demo.integration

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

/**
 * MegaDClient.
 *
 * @author Alex Mikhalochkin
 */
@Component
class MegaDClient(val webClient: WebClient) {

    private val outPorts = setOf(7, 8, 9, 10, 11, 12, 13, 22, 23, 24, 25, 26, 27, 28)

    fun turnOn(portNumber: Int): Mono<Void> {
        return changePortStatus(portNumber, 1)
    }

    fun turnOff(portNumber: Int): Mono<Void> {
        return changePortStatus(portNumber, 0)
    }

    fun getPortStatuses(): Mono<Map<Int, String>> {
        return sendRequest(String::class.java, "all")
            .map { response -> response.split(";") }
            .map { statuses -> outPorts.associateWith { statuses[it] } }
    }

    private fun changePortStatus(portNumber: Int, portStatus: Int): Mono<Void> {
        return sendRequest(Void::class.java, "{port}:{status}", portNumber, portStatus)
    }

    private fun <T> sendRequest(clazz: Class<T>, commandTemplate: String, vararg params: Any): Mono<T> {
        return webClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .queryParam("cmd", commandTemplate)
                    .build(*params)
            }
            .retrieve()
            .bodyToMono(clazz)
    }
}
