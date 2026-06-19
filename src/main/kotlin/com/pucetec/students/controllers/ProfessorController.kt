package com.pucetec.students.controllers

import com.pucetec.students.dto.*
import com.pucetec.students.services.ProfessorService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class ProfessorController(
    val professorService: ProfessorService
) {

    @PostMapping("/api/professors")
    @ResponseStatus(HttpStatus.CREATED)
    fun createProfessor(
        @RequestBody request: ProfessorRequest
    ): ProfessorResponse {

        return professorService.createProfessor(request)
    }

    @GetMapping("/api/professors")
    fun getAllProfessors(): List<ProfessorResponse> {

        return professorService.getAllProfessors()
    }

    @GetMapping("/api/professors/{id}")
    fun getProfessorById(
        @PathVariable id: Long
    ): ProfessorResponse {

        return professorService.getProfessorById(id)
    }

    @PutMapping("/api/professors/{id}")
    fun updateProfessor(
        @PathVariable id: Long,
        @RequestBody request: ProfessorRequest
    ): ProfessorResponse {

        return professorService.updateProfessor(id, request)
    }

    @DeleteMapping("/api/professors/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteProfessor(
        @PathVariable id: Long
    ) {

        professorService.deleteProfessor(id)
    }
}