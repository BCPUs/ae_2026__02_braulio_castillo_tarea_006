package com.pucetec.students.mappers

import com.pucetec.students.dto.EnrollmentResponse
import com.pucetec.students.entities.Enrollment

fun Enrollment.toResponse()= EnrollmentResponse(
    id = this.id,
    createdAt =this.createdAt.toString(),
    status = this.status,
    subject = this.subject.toResponse(),
    student = this.student.toResponse()
)