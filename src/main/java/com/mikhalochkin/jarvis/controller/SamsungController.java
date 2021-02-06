package com.mikhalochkin.jarvis.controller;

import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SamsungController {

    private static final Logger logger = LoggerFactory.getLogger(SamsungController.class);

    @PostMapping(path = "/smartthings")
    public Map<String, Object> handle(@RequestBody Map<String, Object> samsungRequest) {
        logger.info("Process request from samsung. Started. Request={}", samsungRequest);
        Map<String, Object> headers = (Map<String, Object>) samsungRequest.get("headers");
        String interactionType = headers.get("interactionType").toString();
        Map<String, Object> response = new HashMap<>();
        String requestId = headers.get("requestId").toString();
        String interactionResonse;
        if ("discoveryRequest".equals(interactionType)) {
            interactionResonse = "discoveryResponse";
            response.put("requestGrantCallbackAccess", false);
            Map<String, Object> device = new HashMap<>();
            device.put("externalDeviceId", "pdevice-1");
            device.put("deviceCookie", Collections.singletonMap("updatedcookie", "old or new value"));
            device.put("friendlyName", "Kitchen Bulb");
            device.put("manufacturerInfo", ImmutableMap.of("manufacturerName", "LIFX", "modelName", "A19 Color Bulb"));
            device.put("deviceContext", ImmutableMap.of("roomName", "Kitchen", "groups", Arrays.asList("Kitchen Lights",
                    "House Bulbs"), "categories", Arrays.asList("light",
                    "switch")));
            device.put("deviceHandlerType", "c2c-rgbw-color-bulb");
            device.put("deviceUniqueId", "unique identifier of device");

            response.put("devices", Collections.singletonList(device));
        } else if ("stateRefreshRequest".equals(interactionType)) {
            interactionResonse = "stateRefreshResponse";
            Map<String, Object> state = new HashMap<>();
            state.put("externalDeviceId", "pdevice-1");
            state.put("states", Collections.singletonList(ImmutableMap.of("component", "main",
                    "capability", "st.switch",
                    "attribute", "switch",
                    "value", "on")));
            response.put("deviceState", Collections.singletonList(state));
        } else {
            throw new RuntimeException("Unknown interaction type");
        }
        response.put("headers", ImmutableMap.of("schema", "st-schema",
                "version", "1.0",
                "interactionType", interactionResonse,
                "requestId", requestId));
        logger.info("Process request from samsung. Finished. Response={}", response);
        return response;
    }
}
