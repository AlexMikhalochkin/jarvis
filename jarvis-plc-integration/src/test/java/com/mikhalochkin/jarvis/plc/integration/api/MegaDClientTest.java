package com.mikhalochkin.jarvis.plc.integration.api;

import mockit.FullVerifications;
import mockit.Injectable;
import mockit.Tested;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.web.reactive.function.client.WebClient;
import com.mikhalochkin.jarvis.plc.integration.impl.MegaDClient;

public class MegaDClientTest {

    @Injectable
    private WebClient webClient;

    @Tested
    private final PlcClient client = new MegaDClient();

    @Test
    @Ignore
    public void testSendRequest() {
        client.turnOn(7);
        new FullVerifications(webClient){};
    }

    @Test
    @Ignore
    public void testSendRequest2() {
        client.turnOff(7);
        new FullVerifications(webClient){};
    }
}