package com.mikhalochkin.jarvis.service;

import com.mikhalochkin.jarvis.model.Room;
import com.mikhalochkin.jarvis.plc.integration.api.PlcClient;
import mockit.Expectations;
import mockit.FullVerifications;
import mockit.Injectable;
import mockit.Tested;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class LightServiceTest {

    private static LightService bean;

    @Injectable
    private PlcClient plcClient;

    @Tested(fullyInitialized = true)
    private final LightService service = new LightService();

    @Test
    @Disabled
    public void testTurnOnTheLight() {
        new Expectations() {{
            plcClient.turnOn(7);
        }};
        service.turnOn(Room.KITCHEN);
        new FullVerifications(plcClient) {
        };
    }

    @Test
    @Disabled
    public void testTurnOffTheLight() {
        new Expectations() {{
            plcClient.turnOff(8);
        }};
        service.turnOff(Room.LIVING_ROOM);
        new FullVerifications(plcClient) {
        };
    }
}