package com.pucetec.students.entities

import jakarta.persistence.*

@Entity
@Table(name = "subjects")
class Subject(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    var name: String="",

    var code: String = "",

    @ManyToOne(fetch = FetchType.LAZY)
    var professor: Professor,

    @OneToMany(mappedBy = "subject", cascade = [(CascadeType.ALL)], orphanRemoval = true)
    var enrollments: MutableList<Enrollment> = mutableListOf(),

)
