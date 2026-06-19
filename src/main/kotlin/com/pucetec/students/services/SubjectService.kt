package com.pucetec.students.services

import com.pucetec.students.dto.*
import com.pucetec.students.entities.Subject
import com.pucetec.students.exceptions.ProfessorNotFound
import com.pucetec.students.exceptions.SubjectNotFoundException
import com.pucetec.students.repositories.ProfessorRepository
import com.pucetec.students.repositories.SubjectRepository
import org.springframework.stereotype.Service

@Service
class SubjectService(
    private val subjectRepository: SubjectRepository,
    private val professorRepository: ProfessorRepository
) {

    fun createSubject(request: SubjectRequest): SubjectResponse {
        require(request.name.isNotBlank()) { "El nombre de la materia no puede estar en blanco" }
        require(request.code.isNotBlank()) { "El código de la materia no puede estar en blanco" }

        val professor = professorRepository.findById(request.professorId)
            .orElseThrow { ProfessorNotFound("Profesor con ID ${request.professorId} no encontrado") }

        val saved = subjectRepository.save(request.toEntity(professor))
        return saved.toResponse()
    }

    fun getAllSubjects(): List<SubjectResponse> = subjectRepository.findAll().map { it.toResponse() }

    fun getSubjectById(id: Long): SubjectResponse {
        return subjectRepository.findById(id)
            .orElseThrow { SubjectNotFoundException("Materia con ID $id no encontrada") }
            .toResponse()
    }

    fun updateSubject(id: Long, request: SubjectRequest): SubjectResponse {
        require(request.name.isNotBlank()) { "El nombre no puede estar en blanco" }
        require(request.code.isNotBlank()) { "El código no puede estar en blanco" }

        val existing = subjectRepository.findById(id)
            .orElseThrow { SubjectNotFoundException("Materia con ID $id no encontrada") }

        val professor = professorRepository.findById(request.professorId)
            .orElseThrow { ProfessorNotFound("Profesor con ID ${request.professorId} no encontrado") }

        val updatedSubject = Subject(id = existing.id, name = request.name, code = request.code, professor = professor)
        return subjectRepository.save(updatedSubject).toResponse()
    }

    fun deleteSubject(id: Long) {
        val existing = subjectRepository.findById(id)
            .orElseThrow { SubjectNotFoundException("Materia con ID $id no encontrada") }
        subjectRepository.delete(existing)
    }
}