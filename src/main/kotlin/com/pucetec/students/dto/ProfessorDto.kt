package com.pucetec.students.dto

import com.pucetec.students.entities.Professor

data class  ProfessorRequest(
    val name: String,
    val email: String,
)

data class ProfessorResponse(
    val id: Long,
    val name: String,
    val email: String,
)

fun ProfessorRequest.toEntity(): Professor = Professor(name = this.name, email = this.email)
fun Professor.toResponse(): ProfessorResponse = ProfessorResponse(id = this.id, name = this.name, email = this.email)