package com.pucetec.students.services

import com.pucetec.students.dto.StudentRequest
import com.pucetec.students.dto.StudentResponse
import com.pucetec.students.entities.Student
import com.pucetec.students.mappers.toEntity
import com.pucetec.students.repositories.StudentRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class StudentService(
    private val studentRepository: StudentRepository
) {

    private val logger = LoggerFactory.getLogger(StudentService::class.java)

    fun createStudent(request: StudentRequest): StudentResponse {

        logger.info("Creating student ${request.name}")
        logger.info("validating request ${request.name}.... checking email")
        val emailExists = studentRepository.findByEmail(request.email)
        if(emailExists) {
            throw RuntimeException("Email already exists")
        }

        val studentToSave=request.toEntity()

        val savedStudent=studentRepository.save(studentToSave)
        logger.info("saved student with id ${savedStudent.id}")
        val studentEntity = Student(
            name = request.name,
            email = request.email,
        )

        //val savedStudent = studentRepository.save(studentEntity)

        return StudentResponse(
            id = savedStudent.id,
            name = savedStudent.name,
            email = savedStudent.email,
        )
    }

    fun getAllStudents(): List<StudentResponse> {

        logger.info("Getting all student list")



        val savedStudents = studentRepository.findAll()

        return savedStudents.map {
            StudentResponse(
                id = it.id,
                name = it.name,
                email = it.email,
            )
        }
    }
}