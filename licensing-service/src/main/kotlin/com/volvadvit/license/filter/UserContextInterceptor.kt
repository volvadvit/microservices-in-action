package com.volvadvit.license.filter

import com.volvadvit.license.utils.AUTH_TOKEN
import com.volvadvit.license.utils.CORRELATION_ID
import org.slf4j.LoggerFactory
import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse

class UserContextInterceptor: ClientHttpRequestInterceptor {

    private val logger= LoggerFactory.getLogger(UserContextInterceptor::class.java)

    override fun intercept(request: HttpRequest, body: ByteArray, execution: ClientHttpRequestExecution
    ): ClientHttpResponse {
        val headers = request.headers
        headers.add(CORRELATION_ID, UserContextHolder.getContext()?.correlationId)
        headers.add(AUTH_TOKEN, UserContextHolder.getContext()?.authToken)
        logger.info("CorrelationID [${UserContextHolder.getContext()?.correlationId}] was added to outgoing request")

        return execution.execute(request, body)
    }
}