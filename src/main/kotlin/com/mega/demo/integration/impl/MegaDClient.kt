package com.mega.demo.integration.impl

import com.mega.demo.integration.api.PlcClient
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

/**
 * Implementation of [PlcClient] for MegaD controller.
 *
 * @author Alex Mikhalochkin
 */
@Component
class MegaDClient(val webClient: WebClient) : PlcClient {

    private val outPorts = setOf(7, 8, 9, 10, 11, 12, 13, 22, 23, 24, 25, 26, 27, 28)

    override fun turnOn(port: Int) {
        return changePortStatus(port, 1)
    }

    override fun turnOff(port: Int) {
        return changePortStatus(port, 0)
    }

    override fun getPortStatuses(): Map<Int, Boolean> {
        val statuses = sendRequest(String::class.java, "all")
            .block()!!
            .split(";")
        return outPorts.associateWith { ("ON" == statuses[it]) }
    }

    private fun changePortStatus(portNumber: Int, portStatus: Int) {
        sendRequest(Void::class.java, "{port}:{status}", portNumber, portStatus).block()
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
