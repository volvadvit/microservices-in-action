package com.volvadvit.license

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@RefreshScope
// Enables client-side load balancing
@EnableDiscoveryClient
// Enables Netflix Feign clients
@EnableFeignClients
class LicenseServiceApplication

fun main(args: Array<String>) {
	runApplication<LicenseServiceApplication>(*args)
}
