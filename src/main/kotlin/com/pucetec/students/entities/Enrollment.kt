package com.pucetec.students.entities

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "enrollments")
class Enrollment (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(name = "created_at")

    var createdAt: LocalDateTime = LocalDateTime.now(),

    var status: String = "",

    @ManyToOne(fetch = FetchType.LAZY)
    var subject: Subject,

    @ManyToOne(fetch = FetchType.LAZY)
    var student: Student,

    )
