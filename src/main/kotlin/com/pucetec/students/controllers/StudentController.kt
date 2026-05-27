package com.pucetec.students.controllers

import com.pucetec.students.dto.StudentRequest
import com.pucetec.students.dto.StudentResponse
import com.pucetec.students.services.StudentService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
class StudentController(
    val studentService: StudentService
) {

    private val logger = LoggerFactory.getLogger(StudentController::class.java)

    @PostMapping("/api/students")
    fun createStudent(
        @RequestBody request: StudentRequest
    ): StudentResponse {

        logger.info("Creating a new student ${request.name}")

        return studentService.createStudent(request)
    }

    @GetMapping("/api/students")
    fun getAllStudents(): List<StudentResponse> {

        logger.info("Getting all student list")

        return studentService.getAllStudents()
    }
}