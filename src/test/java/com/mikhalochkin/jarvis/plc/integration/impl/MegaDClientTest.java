package com.mikhalochkin.jarvis.plc.integration.impl;

import com.mikhalochkin.jarvis.plc.integration.api.PlcClient;
import mockit.FullVerifications;
import mockit.Injectable;
import mockit.Tested;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

public class MegaDClientTest {

    @Injectable
    private WebClient webClient;

    @Tested
    private final PlcClient client = new MegaDClient();

    @Test
    @Disabled
    public void testSendRequest() {
        client.turnOn(7);
        new FullVerifications(webClient){};
    }

    @Test
    @Disabled
    public void testSendRequest2() {
        client.turnOff(7);
        new FullVerifications(webClient){};
    }
}