package com.mikhalochkin.jarvis.service;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.mikhalochkin.jarvis.plc.integration.PlcClientConfiguration;

@Configuration
@ComponentScan("com.mikhalochkin.jarvis.service.google")
@Import(PlcClientConfiguration.class)
public class ServiceConfiguration {
}
