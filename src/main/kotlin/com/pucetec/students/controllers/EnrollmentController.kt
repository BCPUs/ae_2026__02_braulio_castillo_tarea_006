package com.pucetec.students.controllers

import com.pucetec.students.dto.*
import com.pucetec.students.services.EnrollmentService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class EnrollmentController(
    val enrollmentService: EnrollmentService
) {

    @PostMapping("/api/enrollments")
    @ResponseStatus(HttpStatus.CREATED)
    fun createEnrollment(
        @RequestBody request: EnrollmentRequest
    ): EnrollmentResponse {

        return enrollmentService.createEnrollment(request)
    }

    @GetMapping("/api/enrollments")
    fun getAllEnrollments(): List<EnrollmentResponse> {

        return enrollmentService.getAllEnrollments()
    }

    @GetMapping("/api/enrollments/{id}")
    fun getEnrollmentById(
        @PathVariable id: Long
    ): EnrollmentResponse {

        return enrollmentService.getEnrollmentById(id)
    }

    @PutMapping("/api/enrollments/{id}")
    fun updateEnrollmentStatus(
        @PathVariable id: Long,
        @RequestBody request: EnrollmentUpdateRequest
    ): EnrollmentResponse {

        return enrollmentService.updateEnrollmentStatus(id, request)
    }

    @DeleteMapping("/api/enrollments/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteEnrollment(
        @PathVariable id: Long
    ) {

        enrollmentService.deleteEnrollment(id)
    }
}