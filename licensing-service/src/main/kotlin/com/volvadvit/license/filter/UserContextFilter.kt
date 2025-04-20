package com.volvadvit.license.filter

import com.volvadvit.license.utils.AUTHORIZATION
import com.volvadvit.license.utils.CORRELATION_ID
import com.volvadvit.license.utils.ORGANIZATION_ID
import com.volvadvit.license.utils.USER_ID
import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class UserContextFilter: Filter {

    private val logger = LoggerFactory.getLogger(UserContextFilter::class.java)

    override fun doFilter(servletRequest: ServletRequest?, servletResponse: ServletResponse?, filterChain: FilterChain?) {
        val httpServletRequest = servletRequest as HttpServletRequest

        UserContextHolder.getContext().correlationId = httpServletRequest.getHeader(CORRELATION_ID)
        UserContextHolder.getContext().userId = httpServletRequest.getHeader(USER_ID)
        UserContextHolder.getContext().authToken = httpServletRequest.getHeader(AUTHORIZATION)
        UserContextHolder.getContext().organizationId = httpServletRequest.getHeader(ORGANIZATION_ID)

        logger.info("UserContextFilter Correlation id: ${UserContextHolder.getContext().correlationId}")

        filterChain?.doFilter(httpServletRequest, servletResponse)
    }
}