package com.mikhalochkin.jarvis.service;

import mockit.Expectations;
import mockit.FullVerifications;
import mockit.Injectable;
import mockit.Tested;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.mikhalochkin.jarvis.model.Room;
import com.mikhalochkin.jarvis.plc.integration.api.PlcClient;

@RunWith(JMockit.class)
public class LightServiceTest {

    private static LightService bean;

    @Injectable
    private PlcClient plcClient;

    @Tested(fullyInitialized = true)
    private final LightService service = new LightService();

    @Test
    public void testTurnOnTheLight() {
        new Expectations() {{
            plcClient.turnOn(7);
        }};
        service.turnOn(Room.KITCHEN);
        new FullVerifications(plcClient) {
        };
    }

    @Test
    public void testTurnOffTheLight() {
        new Expectations() {{
            plcClient.turnOff(8);
        }};
        service.turnOff(Room.LIVING_ROOM);
        new FullVerifications(plcClient) {
        };
    }
}