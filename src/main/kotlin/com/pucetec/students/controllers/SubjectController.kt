package com.pucetec.students.controllers

import com.pucetec.students.dto.*
import com.pucetec.students.services.SubjectService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class SubjectController(
    val subjectService: SubjectService
) {

    @PostMapping("/api/subjects")
    @ResponseStatus(HttpStatus.CREATED)
    fun createSubject(
        @RequestBody request: SubjectRequest
    ): SubjectResponse {

        return subjectService.createSubject(request)
    }

    @GetMapping("/api/subjects")
    fun getAllSubjects(): List<SubjectResponse> {

        return subjectService.getAllSubjects()
    }

    @GetMapping("/api/subjects/{id}")
    fun getSubjectById(
        @PathVariable id: Long
    ): SubjectResponse {

        return subjectService.getSubjectById(id)
    }

    @PutMapping("/api/subjects/{id}")
    fun updateSubject(
        @PathVariable id: Long,
        @RequestBody request: SubjectRequest
    ): SubjectResponse {

        return subjectService.updateSubject(id, request)
    }

    @DeleteMapping("/api/subjects/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteSubject(
        @PathVariable id: Long
    ) {

        subjectService.deleteSubject(id)
    }
}