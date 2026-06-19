package com.pucetec.students.controllers

import com.pucetec.students.dto.*
import com.pucetec.students.services.StudentService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class StudentController(
    val studentService: StudentService
) {

    @PostMapping("/api/students")
    @ResponseStatus(HttpStatus.CREATED)
    fun createStudent(
        @RequestBody request: StudentRequest
    ): StudentResponse {

        return studentService.createStudent(request)
    }

    @GetMapping("/api/students")
    fun getAllStudents(): List<StudentResponse> {

        return studentService.getAllStudents()
    }

    @GetMapping("/api/students/{id}")
    fun getStudentById(
        @PathVariable id: Long
    ): StudentResponse {

        return studentService.getStudentById(id)
    }

    @PutMapping("/api/students/{id}")
    fun updateStudent(
        @PathVariable id: Long,
        @RequestBody request: StudentRequest
    ): StudentResponse {

        return studentService.updateStudent(id, request)
    }

    @DeleteMapping("/api/students/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteStudent(
        @PathVariable id: Long
    ) {

        studentService.deleteStudent(id)
    }
}