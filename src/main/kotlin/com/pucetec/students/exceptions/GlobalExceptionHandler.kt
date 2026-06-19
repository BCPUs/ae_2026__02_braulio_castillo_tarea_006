package com.pucetec.students.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistException::class)
    fun handleEmailAlreadyExistException(e: EmailAlreadyExistException): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(message = "Email already exists: ${e.message}")
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error)
    }

    @ExceptionHandler(StudentNotFoundException::class)
    fun handleSubjectNotFoundException(e: StudentNotFoundException): ResponseEntity<ExceptionResponse> {
        val response = ExceptionResponse(
            message = e.message ?: "materia no encontrada - ERROR",
            source = "StudentService"
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response)
    }

    @ExceptionHandler(ProfessorNotFound::class)
    fun handleProfessorNotFound(e: ProfessorNotFound): ResponseEntity<ExceptionResponse> {
        val response = ExceptionResponse(
            message = e.message ?: "profesor no encontrado - ERROR",
            source = "StudentService"
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response)
    }
}

data class ErrorResponse(
    val message: String,
    val timestamp: LocalDateTime = LocalDateTime.now()
)

data class ExceptionResponse(
    val message: String,
    val source: String,
    val timestamp: LocalDateTime = LocalDateTime.now()
)


