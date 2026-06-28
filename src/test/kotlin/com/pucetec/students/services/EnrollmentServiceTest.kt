package com.pucetec.students.services

import com.pucetec.students.dto.EnrollmentRequest
import com.pucetec.students.dto.EnrollmentUpdateRequest
import com.pucetec.students.entities.Enrollment
import com.pucetec.students.entities.Professor
import com.pucetec.students.entities.Student
import com.pucetec.students.entities.Subject
import com.pucetec.students.exceptions.EnrollmentNotFoundException
import com.pucetec.students.exceptions.StudentNotFoundException
import com.pucetec.students.exceptions.SubjectNotFoundException
import com.pucetec.students.repositories.EnrollmentRepository
import com.pucetec.students.repositories.StudentRepository
import com.pucetec.students.repositories.SubjectRepository
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import java.util.Optional
import kotlin.test.Test
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class EnrollmentServiceTest {

    @Mock private lateinit var enrollmentRepository: EnrollmentRepository
    @Mock private lateinit var studentRepository: StudentRepository
    @Mock private lateinit var subjectRepository: SubjectRepository

    @InjectMocks private lateinit var enrollmentService: EnrollmentService

    @Test
    fun `createEnrollment should throw StudentNotFoundException when student does not exist`() {
        val request = EnrollmentRequest(studentId = 1L, subjectId = 2L)
        `when`(studentRepository.findById(1L)).thenReturn(Optional.empty())

        assertThrows<StudentNotFoundException> { enrollmentService.createEnrollment(request) }
    }

    @Test
    fun `createEnrollment should throw SubjectNotFoundException when subject does not exist`() {
        val request = EnrollmentRequest(studentId = 1L, subjectId = 2L)
        val student = Student(id = 1L, name = "Juan", email = "juan@mail.com")

        `when`(studentRepository.findById(1L)).thenReturn(Optional.of(student))
        `when`(subjectRepository.findById(2L)).thenReturn(Optional.empty())

        assertThrows<SubjectNotFoundException> { enrollmentService.createEnrollment(request) }
    }

    @Test
    fun `createEnrollment should save and return response when valid`() {
        val request = EnrollmentRequest(studentId = 1L, subjectId = 2L)
        val student = Student(id = 1L, name = "Juan", email = "juan@mail.com")
        val subject = Subject(id = 2L, name = "Matematicas", code = "M1", professor = Professor())

        val saved = Enrollment(id = 100L, status = "INSCRITO", subject = subject, student = student)

        `when`(studentRepository.findById(1L)).thenReturn(Optional.of(student))
        `when`(subjectRepository.findById(2L)).thenReturn(Optional.of(subject))
        `when`(enrollmentRepository.save(any())).thenReturn(saved)

        val response = enrollmentService.createEnrollment(request)

        assertEquals(100L, response.id)
        assertEquals("INSCRITO", response.status)
    }
    @Test
    fun `getAllEnrollments should return a list of EnrollmentResponse`() {
        // Arrange:
        val student = Student(id = 1L, name = "Juan", email = "juan@mail.com")
        val subject = Subject(id = 2L, name = "Matematicas", code = "M1", professor = Professor())
        val enrollment = Enrollment(id = 100L, status = "INSCRITO", subject = subject, student = student)

        `when`(enrollmentRepository.findAll()).thenReturn(listOf(enrollment))

        // Act
        val result = enrollmentService.getAllEnrollments()

        // Assert
        assertEquals(1, result.size)
        assertEquals("INSCRITO", result[0].status)
        assertEquals("Juan", result[0].student.name)
    }
    @Test
    fun `getEnrollmentById should return EnrollmentResponse when exists`() {
        // Arrange
        val student = Student(id = 1L, name = "Juan", email = "juan@mail.com")
        val subject = Subject(id = 2L, name = "Matematicas", code = "M1", professor = Professor())
        val enrollment = Enrollment(id = 100L, status = "INSCRITO", subject = subject, student = student)

        `when`(enrollmentRepository.findById(100L)).thenReturn(Optional.of(enrollment))

        // Act
        val response = enrollmentService.getEnrollmentById(100L)

        // Assert
        assertEquals(100L, response.id)
        assertEquals("INSCRITO", response.status)
    }

    @Test
    fun `getEnrollmentById should throw EnrollmentNotFoundException when does not exist`() {
        // Arrange
        `when`(enrollmentRepository.findById(999L)).thenReturn(Optional.empty())

        // Act & Assert
        assertThrows<EnrollmentNotFoundException> {
            enrollmentService.getEnrollmentById(999L)
        }
    }

    @Test
    fun `updateEnrollmentStatus should throw IllegalArgumentException when status is blank`() {
        val request = EnrollmentUpdateRequest(status = "  ")
        assertThrows<IllegalArgumentException> { enrollmentService.updateEnrollmentStatus(100L, request) }
    }

    @Test
    fun `updateEnrollmentStatus should throw EnrollmentNotFoundException when not found`() {
        val request = EnrollmentUpdateRequest(status = "APROBADO")
        `when`(enrollmentRepository.findById(100L)).thenReturn(Optional.empty())

        assertThrows<EnrollmentNotFoundException> { enrollmentService.updateEnrollmentStatus(100L, request) }
    }

    @Test
    fun `updateEnrollmentStatus should update and return response when valid`() {
        val request = EnrollmentUpdateRequest(status = "APROBADO")
        val existing = Enrollment(id = 100L, status = "INSCRITO", subject = Subject(professor = Professor()), student = Student(name = "", email = ""))
        val updated = Enrollment(id = 100L, status = "APROBADO", subject = existing.subject, student = existing.student)

        `when`(enrollmentRepository.findById(100L)).thenReturn(Optional.of(existing))
        `when`(enrollmentRepository.save(any())).thenReturn(updated)

        val response = enrollmentService.updateEnrollmentStatus(100L, request)

        assertEquals("APROBADO", response.status)
    }

    @Test
    fun `deleteEnrollment should throw EnrollmentNotFoundException when not found`() {
        `when`(enrollmentRepository.findById(100L)).thenReturn(Optional.empty())
        assertThrows<EnrollmentNotFoundException> { enrollmentService.deleteEnrollment(100L) }
    }

    @Test
    fun `deleteEnrollment should delete when found`() {
        val existing = Enrollment(id = 100L, status = "INSCRITO", subject = Subject(professor = Professor()), student = Student(name = "", email = ""))

        `when`(enrollmentRepository.findById(100L)).thenReturn(Optional.of(existing))

        enrollmentService.deleteEnrollment(100L)

        verify(enrollmentRepository).delete(existing)
    }
}