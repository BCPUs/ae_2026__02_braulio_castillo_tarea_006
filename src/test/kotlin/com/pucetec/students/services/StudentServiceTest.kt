package com.pucetec.students.services

import com.pucetec.students.dto.StudentRequest
import com.pucetec.students.entities.Student
import com.pucetec.students.exceptions.StudentNotFoundException
import com.pucetec.students.exceptions.BlankNameException
import com.pucetec.students.repositories.StudentRepository
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.util.Optional
import kotlin.test.Test
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class StudentServiceTest {

    @Mock
    private lateinit var studentRepository: StudentRepository

    @InjectMocks
    private lateinit var studentService: StudentService

    @Test
    fun `createStudent should throw BlankNameException when name is blank`() {
        // Arrange
        val request = StudentRequest(name = "", email = "test@test.com")

        // Act & Assert
        assertThrows<BlankNameException> {
            studentService.createStudent(request)
        }
    }
    @Test
    fun `createStudent should throw RuntimeException when email already exists`() {
        val request = StudentRequest(name = "Juan", email = "existe@test.com")

        // obligo al mock a decir que el email SÍ existe
        `when`(studentRepository.existsByEmail("existe@test.com")).thenReturn(true)

        assertThrows<RuntimeException> { studentService.createStudent(request) }
    }
    @Test
    fun `createStudent should return a valid response when name is valid`() {
        // Arrange
        val request = StudentRequest(name = "Test", email = "test@test.com")
        val newStudent = Student(id = 1L, name = request.name, email = request.email)

        `when`(studentRepository.save(any(Student::class.java))).thenReturn(newStudent)

        // Act
        val response = studentService.createStudent(request)

        // Assert
        assertEquals(1L, response.id)
        assertEquals(request.name, response.name)
        assertEquals(request.email, response.email)
    }

    @Test
    fun `getAllStudents should return an empty list when there are 0 students`() {
        // Arrange
        `when`(studentRepository.findAll()).thenReturn(emptyList())

        // Act
        val result = studentService.getAllStudents()

        // Assert
        assertEquals(0, result.size)
    }

    @Test
    fun `getAllStudents should return a list of students when there are registered students`() {
        // Arrange
        val students = listOf(
            Student(id = 1L, name = "Test", email = "test@test.com"),
            Student(id = 2L, name = "Test1", email = "test1@test.com")
        )
        `when`(studentRepository.findAll()).thenReturn(students)

        // Act
        val result = studentService.getAllStudents()

        // Assert
        assertEquals(2, result.size)
        assertEquals("Test", result[0].name)
    }

    @Test
    fun `getStudentById should throw StudentNotFoundException when student is not found`() {
        // Arrange
        `when`(studentRepository.findById(67L)).thenReturn(Optional.empty())

        // Act & Assert
        assertThrows<StudentNotFoundException> {
            studentService.getStudentById(67L)
        }
    }

    @Test
    fun `getStudentById should return a valid response when student is present in DB`() {
        // Arrange
        val studentId = 67L
        val student = Student(id = studentId, name = "Juan Perez", email = "juan@test.com")

        `when`(studentRepository.findById(studentId)).thenReturn(Optional.of(student))

        // Act
        val result = studentService.getStudentById(studentId)

        // Assert
        assertEquals(studentId, result.id)
        assertEquals("Juan Perez", result.name)
    }
    @Test
    fun `updateStudent should throw BlankNameException when name is blank`() {
        val request = StudentRequest(name = "", email = "test@test.com")
        assertThrows<BlankNameException> { studentService.updateStudent(1L, request) }
    }

    @Test
    fun `updateStudent should throw StudentNotFoundException when id does not exist`() {
        val request = StudentRequest(name = "Nuevo Nombre", email = "new@test.com")
        `when`(studentRepository.findById(1L)).thenReturn(Optional.empty())

        assertThrows<StudentNotFoundException> { studentService.updateStudent(1L, request) }
    }

    @Test
    fun `updateStudent should return updated student when valid`() {
        val request = StudentRequest(name = "Nuevo Nombre", email = "new@test.com")
        val existing = Student(id = 1L, name = "Viejo", email = "old@test.com")
        val updated = Student(id = 1L, name = "Nuevo Nombre", email = "new@test.com")

        `when`(studentRepository.findById(1L)).thenReturn(Optional.of(existing))
        `when`(studentRepository.save(any())).thenReturn(updated)

        val result = studentService.updateStudent(1L, request)
        assertEquals("Nuevo Nombre", result.name)
    }
    @Test
    fun `deleteStudent should throw StudentNotFoundException when id does not exist`() {
        `when`(studentRepository.findById(1L)).thenReturn(Optional.empty())
        assertThrows<StudentNotFoundException> { studentService.deleteStudent(1L) }
    }

    @Test
    fun `deleteStudent should delete when exists`() {
        val existing = Student(id = 1L, name = "Juan", email = "juan@test.com")
        `when`(studentRepository.findById(1L)).thenReturn(Optional.of(existing))

        studentService.deleteStudent(1L)
        verify(studentRepository).delete(existing)
    }
}

