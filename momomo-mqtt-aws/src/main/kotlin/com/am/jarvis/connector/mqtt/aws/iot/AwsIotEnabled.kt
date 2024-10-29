package com.am.jarvis.connector.mqtt.aws.iot

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty

/**
 * Enables connection to the AWS IoT.
 *
 * @author Alex Mikhalochkin
 */
@ConditionalOnProperty(name = ["mqtt.active"], havingValue = "aws")
annotation class AwsIotEnabled
