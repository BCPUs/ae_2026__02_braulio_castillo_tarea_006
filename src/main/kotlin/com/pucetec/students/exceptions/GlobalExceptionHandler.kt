package com.pucetec.students.exceptions

import org.aspectj.bridge.Message
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(EmailAlreadyExistException::class)
    fun handleEmailAlreadyExistException(e: EmailAlreadyExistException): ResponseEntity <ErrorResponse> {
        val error=ErrorResponse(
            message = "Email already exists: ${e.message}",
        )
        //estamos retornando nuestro error response con un codigo de error http 400 (bad request)
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(error)
    }
}

data class ErrorResponse(
    val message: String,
    val timestamp: LocalDateTime=LocalDateTime.now()
)