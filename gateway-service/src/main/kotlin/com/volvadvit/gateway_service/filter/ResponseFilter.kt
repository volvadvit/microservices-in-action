package com.volvadvit.gateway_service.filter

import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Mono


@Configuration
class ResponseFilter(private val filterUtils: FilterUtils) {

    private val logger = LoggerFactory.getLogger(ResponseFilter::class.java)

    @Bean
    fun postGlobalFilter(): GlobalFilter =
        GlobalFilter { exchange, chain ->
            chain
                .filter(exchange)
                .then(Mono.fromRunnable {
                    val requestHeaders = exchange.request.headers
                    val correlationId = filterUtils.getCorrelationId(requestHeaders)
                    logger.info("Adding the correlation id to the outbound header: $correlationId")
                    exchange.response.headers.add(FilterUtils.CORRELATION_ID, correlationId)
                    logger.info("Completing outgoing request for: ${exchange.request.uri}.")
                })
        }
}