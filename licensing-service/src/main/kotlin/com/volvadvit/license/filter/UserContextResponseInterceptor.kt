package com.volvadvit.license.filter

import com.volvadvit.license.utils.CORRELATION_ID
import org.slf4j.LoggerFactory
import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse

class UserContextResponseInterceptor: ClientHttpRequestInterceptor {

    private val logger= LoggerFactory.getLogger(UserContextResponseInterceptor::class.java)

    override fun intercept(request: HttpRequest, body: ByteArray, execution: ClientHttpRequestExecution
    ): ClientHttpResponse {
        val headers = request.headers
        headers.add(CORRELATION_ID, UserContextHolder.getContext().correlationId)
        logger.info("CorrelationID [${UserContextHolder.getContext().correlationId}] was added to response")

        return execution.execute(request, body)
    }
}