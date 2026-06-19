package com.pucetec.students.controllers

import com.pucetec.students.dto.*
import com.pucetec.students.services.ProfessorService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/professors")
class ProfessorController(private val professorService: ProfessorService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createProfessor(@RequestBody request: ProfessorRequest): ProfessorResponse = professorService.createProfessor(request)

    @GetMapping
    fun getAllProfessors(): List<ProfessorResponse> = professorService.getAllProfessors()

    @GetMapping("/{id}")
    fun getProfessorById(@PathVariable id: Long): ProfessorResponse = professorService.getProfessorById(id)

    @PutMapping("/{id}")
    fun updateProfessor(@PathVariable id: Long, @RequestBody request: ProfessorRequest): ProfessorResponse =
        professorService.updateProfessor(id, request)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteProfessor(@PathVariable id: Long) = professorService.deleteProfessor(id)
}