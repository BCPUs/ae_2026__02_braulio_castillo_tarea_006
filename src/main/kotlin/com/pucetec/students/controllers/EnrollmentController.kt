package com.pucetec.students.controllers

import com.pucetec.students.dto.*
import com.pucetec.students.services.EnrollmentService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/enrollments")
class EnrollmentController(private val enrollmentService: EnrollmentService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createEnrollment(@RequestBody request: EnrollmentRequest): EnrollmentResponse = enrollmentService.createEnrollment(request)

    @GetMapping
    fun getAllEnrollments(): List<EnrollmentResponse> = enrollmentService.getAllEnrollments()

    @GetMapping("/{id}")
    fun getEnrollmentById(@PathVariable id: Long): EnrollmentResponse = enrollmentService.getEnrollmentById(id)

    @PutMapping("/{id}")
    fun updateEnrollmentStatus(@PathVariable id: Long, @RequestBody request: EnrollmentUpdateRequest): EnrollmentResponse =
        enrollmentService.updateEnrollmentStatus(id, request)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteEnrollment(@PathVariable id: Long) = enrollmentService.deleteEnrollment(id)
}