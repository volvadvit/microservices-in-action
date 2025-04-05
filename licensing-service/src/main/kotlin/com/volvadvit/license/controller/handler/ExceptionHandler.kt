package com.volvadvit.license.controller.handler

import io.github.resilience4j.circuitbreaker.CallNotPermittedException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.concurrent.TimeoutException

@ControllerAdvice
class ExceptionHandler: ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [TimeoutException::class])
    @ResponseBody
    fun handleException(request: HttpServletRequest, error: TimeoutException): ResponseEntity<Map<String, String?>> {
        return ResponseEntity
            .internalServerError()
            .body(mapOf(
                "error" to error.message,
                "status" to HttpStatus.NOT_ACCEPTABLE.name,
                "path" to request.requestURL.toString(),
                "timestamp" to System.currentTimeMillis().toString()))
    }

    @ExceptionHandler(value = [CallNotPermittedException::class])
    @ResponseBody
    fun handleCircuitBreakerException(
        request: HttpServletRequest,
        error: CallNotPermittedException
    ): ResponseEntity<Map<String, String?>> {
        return ResponseEntity
            .internalServerError()
            .body(mapOf(
                "error" to error.message,
                "status" to HttpStatus.NOT_ACCEPTABLE.name,
                "path" to request.requestURL.toString(),
                "timestamp" to System.currentTimeMillis().toString()))
    }
}