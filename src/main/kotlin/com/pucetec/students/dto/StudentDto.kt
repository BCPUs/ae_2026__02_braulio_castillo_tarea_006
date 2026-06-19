package com.pucetec.students.dto

import com.pucetec.students.entities.Student

/**
 * {name: ana, email: ana@puce.com}
 *
 */
data class StudentRequest(
    val name: String,
    val email: String,
)
/**
 *
 * {id:1, name: ana, email: ana@puce.com}
 *
 */
data class StudentResponse(
    val id: Long,
    val name: String,
    val email: String,
)

fun StudentRequest.toEntity(): Student = Student(name = this.name, email = this.email)
fun Student.toResponse(): StudentResponse = StudentResponse(id = this.id, name = this.name, email = this.email)