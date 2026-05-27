package com.pucetec.students.services
import com.pucetec.students.dto.StudentRequest
import com.pucetec.students.dto.StudentResponse
import com.pucetec.students.entities.Student
import com.pucetec.students.repositories.StudentRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

//almacena la logica del negocio
@Service
class StudentService (
    private val studentRepository: StudentRepository){

    private val logger = LoggerFactory.getLogger(StudentService::class.java)

    fun createStudent(request: StudentRequest): StudentResponse {
        logger.info("Creating student ${request.name}")
        //validar
        //TODO: validar que el estudiante no existe previamente
        //crear entidad
        val studentEntity = Student(
            name = request.name,
            email = request.email,
        )
        //guardar entidad
        val savedStudent = studentRepository.save(studentEntity)

        //retornar response
        return studentResponse(
            id=savedStudent.id,
            name=savedStudent.name,
            email=savedStudent.email,
        )
    }

}

fun getAllStudents(): List<StudentResponse> {
    logger.info("Getting all student list")
    //consultar todos los estudiantes

    val savedStudents = studentRepository.findAll()

    //convertir al response adecuado

    return savedStudents.map {
        StudentResponse(
            id = it.id,
            name = it.name,
            email = it.email,
        )
    }
}