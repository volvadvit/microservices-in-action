package com.volvadvit.license.config

import com.volvadvit.license.filter.UserContextInterceptor
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate


@Configuration
class RestTemplateConfiguration {

    @LoadBalanced
    @Bean
    fun getRestTemplate(): RestTemplate {
        val template = RestTemplate()
        val interceptors = template.interceptors
        interceptors.add(UserContextInterceptor())
        template.interceptors = interceptors

        return template
    }
}