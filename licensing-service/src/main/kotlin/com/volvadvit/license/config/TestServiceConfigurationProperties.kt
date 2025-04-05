package com.volvadvit.license.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.context.annotation.Configuration

@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "test")
class TestServiceConfigurationProperties {
    lateinit var property: String
}