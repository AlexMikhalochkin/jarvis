package com.am.jarvis.connector.megad.mqtt

import com.am.jarvis.connector.megad.repository.api.DeviceRepository
import mu.KotlinLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

private val logger = KotlinLogging.logger {}

/**
 * Worker for refreshing stored states.
 *
 * @author Alex Mikhalochkin
 */
@Service
class RefreshStoredStatesWorker(
    private val deviceRepository: DeviceRepository,
    private val commandPublisher: MegaDMqttCommandMessagePublisher
) {

    @Scheduled(
        fixedRateString = "\${megad.states.refresh-interval-minutes}",
        initialDelayString = "\${megad.states.initial-delay-minutes}",
        timeUnit = TimeUnit.MINUTES
    )
    fun refresh() {
        logger.info("Refreshing stored states. Started.")
        deviceRepository.findAllPorts()
            .map { "get:$it" }
            .forEach(commandPublisher::publish)
    }
}
