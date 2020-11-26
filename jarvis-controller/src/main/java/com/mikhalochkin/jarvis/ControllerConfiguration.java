package com.mikhalochkin.jarvis;

import com.mikhalochkin.jarvis.service.ServiceConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Configuration for the controller module.
 *
 * @author Alex Mikhalochkin
 */
@Configuration
@ComponentScan("com.mikhalochkin.jarvis.controller")
@Import(ServiceConfiguration.class)
public class ControllerConfiguration {
}
