package com.mikhalochkin.jarvis.plc.integration;

import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.Assert.assertNotNull;

/**
 * Verifies {@link PlcClientConfiguration}.
 *
 * @author Alex Mikhalochkin
 */
class PlcClientConfigurationTest {

    @Test
    void testWebClient() {
        PlcClientConfiguration plcClientConfiguration = new PlcClientConfiguration();
        WebClient webClient = plcClientConfiguration.webClient("http://test-url");
        assertNotNull(webClient);
    }
}
