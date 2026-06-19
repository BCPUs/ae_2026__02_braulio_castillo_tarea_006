package com.pucetec.students.controllers

import com.pucetec.students.dto.*
import com.pucetec.students.services.SubjectService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/subjects")
class SubjectController(private val subjectService: SubjectService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createSubject(@RequestBody request: SubjectRequest): SubjectResponse = subjectService.createSubject(request)

    @GetMapping
    fun getAllSubjects(): List<SubjectResponse> = subjectService.getAllSubjects()

    @GetMapping("/{id}")
    fun getSubjectById(@PathVariable id: Long): SubjectResponse = subjectService.getSubjectById(id)

    @PutMapping("/{id}")
    fun updateSubject(@PathVariable id: Long, @RequestBody request: SubjectRequest): SubjectResponse =
        subjectService.updateSubject(id, request)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteSubject(@PathVariable id: Long) = subjectService.deleteSubject(id)
}