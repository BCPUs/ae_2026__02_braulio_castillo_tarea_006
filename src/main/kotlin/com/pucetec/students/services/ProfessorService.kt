package com.pucetec.students.services

import com.pucetec.students.dto.*
import com.pucetec.students.entities.Professor
import com.pucetec.students.exceptions.ProfessorNotFound
import com.pucetec.students.repositories.ProfessorRepository
import org.springframework.stereotype.Service

@Service
class ProfessorService(private val professorRepository: ProfessorRepository) {

    fun createProfessor(request: ProfessorRequest): ProfessorResponse {
        require(request.name.isNotBlank()) { "El nombre no puede estar en blanco" }
        val saved = professorRepository.save(request.toEntity())
        return saved.toResponse()
    }

    fun getAllProfessors(): List<ProfessorResponse> = professorRepository.findAll().map { it.toResponse() }

    fun getProfessorById(id: Long): ProfessorResponse {
        return professorRepository.findById(id)
            .orElseThrow { ProfessorNotFound("Profesor con ID $id no encontrado") }
            .toResponse()
    }

    fun updateProfessor(id: Long, request: ProfessorRequest): ProfessorResponse {
        require(request.name.isNotBlank()) { "El nombre no puede estar en blanco" }
        val existing = professorRepository.findById(id)
            .orElseThrow { ProfessorNotFound("Profesor con ID $id no encontrado") }

        val updatedProfessor = Professor(id = existing.id, name = request.name, email = request.email)
        return professorRepository.save(updatedProfessor).toResponse()
    }

    fun deleteProfessor(id: Long) {
        val existing = professorRepository.findById(id)
            .orElseThrow { ProfessorNotFound("Profesor con ID $id no encontrado") }
        professorRepository.delete(existing)
    }
}