package com.pucetec.students.services

import com.pucetec.students.dto.*
import com.pucetec.students.entities.Enrollment
import com.pucetec.students.exceptions.EnrollmentNotFoundException
import com.pucetec.students.exceptions.StudentNotFoundException
import com.pucetec.students.exceptions.SubjectNotFoundException
import com.pucetec.students.repositories.EnrollmentRepository
import com.pucetec.students.repositories.StudentRepository
import com.pucetec.students.repositories.SubjectRepository
import org.springframework.stereotype.Service

@Service
class EnrollmentService(
    private val enrollmentRepository: EnrollmentRepository,
    private val studentRepository: StudentRepository,
    private val subjectRepository: SubjectRepository
) {
    fun createEnrollment(request: EnrollmentRequest): EnrollmentResponse {
        val student = studentRepository.findById(request.studentId)
            .orElseThrow { StudentNotFoundException("Estudiante con ID ${request.studentId} no encontrado") }

        val subject = subjectRepository.findById(request.subjectId)
            .orElseThrow { SubjectNotFoundException("Materia con ID ${request.subjectId} no encontrada") }

        val enrollment = Enrollment(
            status = "INSCRITO",
            subject = subject, // Orden correcto según la entidad
            student = student  // Orden correcto según la entidad
        )
        return enrollmentRepository.save(enrollment).toResponse()
    }
    fun getAllEnrollments(): List<EnrollmentResponse> = enrollmentRepository.findAll().map { it.toResponse() }

    fun getEnrollmentById(id: Long): EnrollmentResponse {
        return enrollmentRepository.findById(id)
            .orElseThrow { EnrollmentNotFoundException("Inscripción con ID $id no encontrada") }
            .toResponse()
    }
    fun updateEnrollmentStatus(id: Long, request: EnrollmentUpdateRequest): EnrollmentResponse {
        require(request.status.isNotBlank()) { "El estado no puede estar en blanco" }
        val existing = enrollmentRepository.findById(id)
            .orElseThrow { EnrollmentNotFoundException("Inscripción con ID $id no encontrada") }

        val updated = Enrollment(
            id = existing.id,
            createdAt = existing.createdAt,
            status = request.status,
            subject = existing.subject, // Orden correcto según la entidad
            student = existing.student   // Orden correcto según la entidad
        )
        return enrollmentRepository.save(updated).toResponse()
    }
    fun deleteEnrollment(id: Long) {
        val existing = enrollmentRepository.findById(id)
            .orElseThrow { EnrollmentNotFoundException("Inscripción con ID $id no encontrada") }
        enrollmentRepository.delete(existing)
    }
}