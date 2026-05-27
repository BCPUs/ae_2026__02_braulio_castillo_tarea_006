package com.pucetec.students.repositories

import com.pucetec.students.dto.StudentRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StudentRepository: JpaRepository<StudentRequest, Long>