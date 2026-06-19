package com.pucetec.students.entities

import jakarta.persistence.*

@Entity
@Table(name = "students")
class Student(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    var name: String,

    var email: String,
)
