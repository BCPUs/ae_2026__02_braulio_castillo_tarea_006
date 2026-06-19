package com.pucetec.students.services

import com.pucetec.students.dto.*
import com.pucetec.students.entities.Student
import com.pucetec.students.exceptions.StudentNotFoundException
import com.pucetec.students.repositories.StudentRepository
import org.springframework.stereotype.Service

@Service
class StudentService(private val studentRepository: StudentRepository) {

    fun createStudent(request: StudentRequest): StudentResponse {
        require(request.name.isNotBlank()) { "El nombre no puede estar en blanco" }
        if (studentRepository.existsByEmail(request.email)) {
            throw RuntimeException("Email already exists")
        }
        val saved = studentRepository.save(request.toEntity())
        return saved.toResponse()
    }

    fun getAllStudents(): List<StudentResponse> = studentRepository.findAll().map { it.toResponse() }

    fun getStudentById(id: Long): StudentResponse {
        return studentRepository.findById(id)
            .orElseThrow { StudentNotFoundException("Estudiante con ID $id no encontrado") }
            .toResponse()
    }

    fun updateStudent(id: Long, request: StudentRequest): StudentResponse {
        require(request.name.isNotBlank()) { "El nombre no puede estar en blanco" }
        val existing = studentRepository.findById(id)
            .orElseThrow { StudentNotFoundException("Estudiante con ID $id no encontrado") }

        val updatedStudent = Student(id = existing.id, name = request.name, email = request.email)
        return studentRepository.save(updatedStudent).toResponse()
    }

    fun deleteStudent(id: Long) {
        val existing = studentRepository.findById(id)
            .orElseThrow { StudentNotFoundException("Estudiante con ID $id no encontrado") }
        studentRepository.delete(existing)
    }



}