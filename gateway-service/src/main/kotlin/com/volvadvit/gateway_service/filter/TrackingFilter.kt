package com.volvadvit.gateway_service.filter

import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.util.UUID


@Order(1)
@Component
class TrackingFilter(private val filterUtils: FilterUtils): GlobalFilter {

    private val logger = LoggerFactory.getLogger(TrackingFilter::class.java)

    override fun filter(exchange: ServerWebExchange?, chain: GatewayFilterChain?): Mono<Void> {
        val requestHeaders: HttpHeaders = exchange!!.request.headers
        var exchangeWithCorrelationID = exchange

        if (isCorrelationIdPresent(requestHeaders)) {
            logger.info("tmx-correlation-id found in tracking filter: ${filterUtils.getCorrelationId(requestHeaders)}.")
        } else {
            val correlationID: String = generateCorrelationId()
            exchangeWithCorrelationID = filterUtils.setCorrelationId(exchange, correlationID)
            logger.info("tmx-correlation-id generated in tracking filter: ${correlationID}.")
        }

        return chain!!.filter(exchangeWithCorrelationID)
    }

    private fun isCorrelationIdPresent(requestHeaders: HttpHeaders): Boolean =
        filterUtils.getCorrelationId(requestHeaders) != null

    private fun generateCorrelationId(): String = UUID.randomUUID().toString()
}