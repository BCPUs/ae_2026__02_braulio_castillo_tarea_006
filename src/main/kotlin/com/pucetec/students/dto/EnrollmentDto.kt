package com.pucetec.students.dto

import com.pucetec.students.entities.Enrollment

data class EnrollmentRequest(val studentId: Long, val subjectId: Long)
data class EnrollmentUpdateRequest(val status: String)
data class EnrollmentResponse(
    val id: Long,
    val createdAt: String,
    val status: String,
    val student: StudentResponse,
    val subject: SubjectResponse
)

fun Enrollment.toResponse(): EnrollmentResponse = EnrollmentResponse(
    id = this.id,
    createdAt = this.createdAt,
    status = this.status,
    student = this.student.toResponse(),
    subject = this.subject.toResponse()
)