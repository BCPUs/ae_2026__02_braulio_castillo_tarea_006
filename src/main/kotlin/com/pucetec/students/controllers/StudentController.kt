package com.pucetec.students.controllers

import com.pucetec.students.dto.*
import com.pucetec.students.services.StudentService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/students")
class StudentController(private val studentService: StudentService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createStudent(@RequestBody request: StudentRequest): StudentResponse = studentService.createStudent(request)

    @GetMapping
    fun getAllStudents(): List<StudentResponse> = studentService.getAllStudents()

    @GetMapping("/{id}")
    fun getStudentById(@PathVariable id: Long): StudentResponse = studentService.getStudentById(id)

    @PutMapping("/{id}")
    fun updateStudent(@PathVariable id: Long, @RequestBody request: StudentRequest): StudentResponse =
        studentService.updateStudent(id, request)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteStudent(@PathVariable id: Long) = studentService.deleteStudent(id)
}