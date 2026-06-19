package com.pucetec.students.dto

import com.pucetec.students.entities.Professor
import com.pucetec.students.entities.Subject

data class SubjectRequest(
    val name: String,
    val code: String,
    val professorId: Long,
)

data class SubjectResponse(
    val name: String,
    val code: String,
    val professor: ProfessorResponse,
    val id: Long,
)

fun SubjectRequest.toEntity(professor: Professor): Subject = Subject(
    name = this.name,
    code = this.code,
    professor = professor,
)

fun Subject.toResponse(): SubjectResponse = SubjectResponse(
    id = this.id,
    name = this.name,
    code = this.code,
    professor = this.professor.toResponse(),
)