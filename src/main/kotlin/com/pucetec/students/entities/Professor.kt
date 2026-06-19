package com.pucetec.students.entities

import jakarta.persistence.*

@Entity
@Table(name = "professors")
class Professor (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    val name: String = "",

    val email: String = "",

    //relacion que une, uno a muchos
    @OneToMany(mappedBy = "professor", cascade = [(CascadeType.ALL)], orphanRemoval = true)

    val subjects: MutableList<Subject> = mutableListOf(),
)
