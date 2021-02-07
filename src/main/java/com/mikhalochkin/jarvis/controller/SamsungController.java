package com.mikhalochkin.jarvis.controller;

import com.google.common.collect.ImmutableMap;
import com.mikhalochkin.jarvis.service.api.SmartHomeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class SamsungController {

    @Autowired
    @Qualifier("samsungService")
    private SmartHomeService samsungService;

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
            response.put("requestGrantCallbackAccess", true);
            Map<String, Object> device = new HashMap<>();
            device.put("externalDeviceId", "kitchen-light-0");
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
            state.put("externalDeviceId", "kitchen-light-0");
            String isOn = samsungService.getStatuses(Collections.singletonList("kitchen-light-0")).get("kitchen-light-0").toString();
            state.put("states", Collections.singletonList(ImmutableMap.of("component", "main",
                    "capability", "st.switch",
                    "attribute", "switch",
                    "value", isOn)));
            response.put("deviceState", Collections.singletonList(state));
        } else if ("commandRequest".equals(interactionType)) {
            interactionResonse = "commandResponse";
            Map<String, Object> state = new HashMap<>();
            state.put("externalDeviceId", "kitchen-light-0");
            List<Object> devices = (List<Object>) samsungRequest.get("devices");
            Map<String, Object> device = (Map<String, Object>) devices.get(0);
            List<Object> commands = (List<Object>) device.get("commands");
            Map<String, Object> command = (Map<String, Object>) commands.get(0);
            boolean isOn = "on".equals(command.get("command").toString());
            samsungService.changeStatus(Collections.singletonList("kitchen-light-0"), isOn);
            state.put("states", Collections.singletonList(ImmutableMap.of("component", "main",
                    "capability", "st.switch",
                    "attribute", "switch",
                    "value", isOn ? "on" : "off")));
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
