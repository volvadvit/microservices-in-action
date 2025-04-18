package com.volvadvit.organization

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.context.config.annotation.RefreshScope

@SpringBootApplication
@RefreshScope
class OrganizationServiceApplication

fun main(args: Array<String>) {
	runApplication<OrganizationServiceApplication>(*args)
}
