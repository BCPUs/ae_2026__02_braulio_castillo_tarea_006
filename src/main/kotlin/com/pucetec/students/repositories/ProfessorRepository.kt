package com.pucetec.students.repositories

import com.pucetec.students.entities.Professor
import org.springframework.data.jpa.repository.JpaRepository

interface ProfessorRepository : JpaRepository <Professor, Long>{

}