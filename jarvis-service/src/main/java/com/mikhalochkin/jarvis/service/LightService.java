package com.mikhalochkin.jarvis.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mikhalochkin.jarvis.model.Room;
import com.mikhalochkin.jarvis.plc.integration.api.PlcClient;

@Service
public class LightService {

    private static final Logger logger = LoggerFactory.getLogger(LightService.class);

    @Autowired
    private PlcClient plcClient;

    public void turnOn(Room room) {
        plcClient.turnOn(room.getPort());
    }

    public void turnOff(Room room) {
        plcClient.turnOff(room.getPort());
    }
}
