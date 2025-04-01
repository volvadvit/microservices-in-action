package com.volvadvit.license.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "test")
class TestServiceProperties {
    lateinit var property: String
}