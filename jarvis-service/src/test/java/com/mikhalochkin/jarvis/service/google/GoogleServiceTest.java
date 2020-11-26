package com.mikhalochkin.jarvis.service.google;


import com.mikhalochkin.jarvis.model.Device;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

class GoogleServiceTest {

    @Test
    @Disabled
    public void name() throws IOException {
        GoogleService googleService = new GoogleService();
        List<Device> allDevices = googleService.getAllDevices();
        assertEquals(2, allDevices.size());
    }
}