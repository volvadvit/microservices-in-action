package com.volvadvit.organization.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.volvadvit.organization.model.UpdateOrganizationEvent
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import java.util.function.Function


@Configuration
class CloudStreamKafkaProducerConfig(private val objectMapper: ObjectMapper) {

    private val logger = LoggerFactory.getLogger(CloudStreamKafkaProducerConfig::class.java)

    @Bean
    fun consume(): Function<Message<*>, String> {
        return Function<Message<*>, String> { message ->
            val payload = message.payload
            logger.info("Processed message: $payload")
            //TODO map payload to specific object type
            return@Function payload.toString()
        }
    }

    @Bean
    fun publish(event: UpdateOrganizationEvent): Function<String, String> {
        return Function<String, String> {
            logger.info("Publishing event: $event")
            return@Function objectMapper.writeValueAsString(event)
        }
    }
}