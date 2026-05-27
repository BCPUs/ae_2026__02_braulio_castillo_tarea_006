package com.pucetec.students.mappers

import com.pucetec.students.dto.StudentRequest
import com.pucetec.students.dto.StudentResponse
import com.pucetec.students.entities.Student

fun StudentRequest.toEntity(): Student {
    return Student (
        name = name,
        email = email,
    )
}

fun Student.toResponse(): StudentResponse {
    return StudentResponse(
        id = id,
        name = name,
        email = email,
    )
}