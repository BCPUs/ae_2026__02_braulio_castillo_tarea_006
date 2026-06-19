package com.pucetec.students.entities

import jakarta.persistence.*

@Entity
@Table(name = "professors")
class Professor (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    var name: String = "",

    var email: String = "",

    //relacion que une, uno a muchos
    @OneToMany(mappedBy = "professor", cascade = [(CascadeType.ALL)], orphanRemoval = true)

    var subjects: MutableList<Subject> = mutableListOf(),
)
