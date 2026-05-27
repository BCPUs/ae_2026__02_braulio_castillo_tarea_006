package com.pucetec.students.exceptions

class EmailAlreadyExistException (
    override val message: String,
) : RuntimeException(message)