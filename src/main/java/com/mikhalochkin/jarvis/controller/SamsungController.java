package com.mikhalochkin.jarvis.controller;

import com.google.common.collect.ImmutableMap;
import com.mikhalochkin.jarvis.model.SamsungRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SamsungController {

    private static final Logger logger = LoggerFactory.getLogger(SamsungController.class);

    @PostMapping(path = "/smartthings")
    public Map<String, Object> handle(@RequestBody SamsungRequest samsungRequest) {
        logger.info("Process request from samsung. Started. Request={}", samsungRequest);
        Map<String, Object> response = ImmutableMap.of("targetUrl", "{TARGET_URL}");
        logger.info("Process request from samsung. Started. Response={}", response);
        return response;
    }
}
