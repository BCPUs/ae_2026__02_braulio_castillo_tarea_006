package com.pucetec.students.services

import com.pucetec.students.dto.SubjectRequest
import com.pucetec.students.entities.Professor
import com.pucetec.students.entities.Subject
import com.pucetec.students.exceptions.ProfessorNotFound
import com.pucetec.students.exceptions.SubjectNotFoundException
import com.pucetec.students.repositories.ProfessorRepository
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
class SubjectServiceTest {

    @Mock
    private lateinit var subjectRepository: SubjectRepository

    @Mock
    private lateinit var professorRepository: ProfessorRepository

    @InjectMocks
    private lateinit var subjectService: SubjectService

    // --- TESTS: createSubject ---

    @Test
    fun `createSubject should throw IllegalArgumentException when name is blank`() {
        // Arrange
        val request = SubjectRequest(name = "   ", code = "MAT-101", professorId = 1L)

        // Act & Assert
        assertThrows<IllegalArgumentException> { subjectService.createSubject(request) }
    }

    @Test
    fun `createSubject should throw IllegalArgumentException when code is blank`() {
        // Arrange
        val request = SubjectRequest(name = "Matematicas", code = "", professorId = 1L)

        // Act & Assert
        assertThrows<IllegalArgumentException> { subjectService.createSubject(request) }
    }

    @Test
    fun `createSubject should throw ProfessorNotFound when professor does not exist`() {
        // Arrange
        val request = SubjectRequest(name = "Matematicas", code = "MAT-101", professorId = 99L)
        `when`(professorRepository.findById(99L)).thenReturn(Optional.empty())

        // Act & Assert
        assertThrows<ProfessorNotFound> { subjectService.createSubject(request) }
    }

    @Test
    fun `createSubject should save and return SubjectResponse when request is valid`() {
        // Arrange
        val request = SubjectRequest(name = "Matematicas", code = "MAT-101", professorId = 1L)
        val professor = Professor(id = 1L, name = "Dr. Alex Ruales", email = "aruales@mail.com")
        val savedSubject = Subject(id = 10L, name = "Matematicas", code = "MAT-101", professor = professor)

        `when`(professorRepository.findById(1L)).thenReturn(Optional.of(professor))
        `when`(subjectRepository.save(any(Subject::class.java))).thenReturn(savedSubject)

        // Act
        val response = subjectService.createSubject(request)

        // Assert
        assertEquals(10L, response.id)
        assertEquals("Matematicas", response.name)
        assertEquals("MAT-101", response.code)
        assertEquals("Dr. Alex Ruales", response.professor.name)
    }

    //TESTS: getAllSubjects
    @Test
    fun `getAllSubjects should return mapped list of subject responses`() {
        // Arrange
        val professor = Professor(id = 1L, name = "Dr. Alex Ruales", email = "aruales@test.com")
        val subjects = listOf(
            Subject(id = 10L, name = "Matematicas", code = "MAT-101", professor = professor)
        )
        `when`(subjectRepository.findAll()).thenReturn(subjects)

        // Act
        val result = subjectService.getAllSubjects()

        // Assert
        assertEquals(1, result.size)
        assertEquals("Matematicas", result[0].name)
    }

    // --- TESTS: getSubjectById ---

    @Test
    fun `getSubjectById should throw SubjectNotFoundException when subject does not exist`() {
        // Arrange
        `when`(subjectRepository.findById(50L)).thenReturn(Optional.empty())

        // Act & Assert
        assertThrows<SubjectNotFoundException> { subjectService.getSubjectById(50L) }
    }

    @Test
    fun `getSubjectById should return SubjectResponse when subject exists`() {
        // Arrange
        val professor = Professor(id = 1L, name = "Dr. Alex Ruales", email = "aruales@test.com")
        val subject = Subject(id = 50L, name = "Historia", code = "HIS-202", professor = professor)
        `when`(subjectRepository.findById(50L)).thenReturn(Optional.of(subject))

        // Act
        val response = subjectService.getSubjectById(50L)

        // Assert
        assertEquals(50L, response.id)
        assertEquals("Historia", response.name)
    }

    //TESTS: updateSubject

    @Test
    fun `updateSubject should throw IllegalArgumentException when name is blank`() {
        // Arrange
        val request = SubjectRequest(name = "", code = "CODE", professorId = 1L)

        // Act & Assert
        assertThrows<IllegalArgumentException> { subjectService.updateSubject(10L, request) }
    }

    @Test
    fun `updateSubject should throw IllegalArgumentException when code is blank`() {
        // Arrange
        val request = SubjectRequest(name = "Name", code = "  ", professorId = 1L)

        // Act & Assert
        assertThrows<IllegalArgumentException> { subjectService.updateSubject(10L, request) }
    }

    @Test
    fun `updateSubject should throw SubjectNotFoundException when subject to update does not exist`() {
        // Arrange
        val request = SubjectRequest(name = "Matematicas II", code = "MAT-102", professorId = 1L)
        `when`(subjectRepository.findById(10L)).thenReturn(Optional.empty())

        // Act & Assert
        assertThrows<SubjectNotFoundException> { subjectService.updateSubject(10L, request) }
    }

    @Test
    fun `updateSubject should throw ProfessorNotFound when new professor does not exist`() {
        // Arrange
        val request = SubjectRequest(name = "Matematicas II", code = "MAT-102", professorId = 99L)
        val existingSubject = Subject(id = 10L, name = "Matematicas", code = "MAT-101", professor = Professor())

        `when`(subjectRepository.findById(10L)).thenReturn(Optional.of(existingSubject))
        `when`(professorRepository.findById(99L)).thenReturn(Optional.empty())

        // Act & Assert
        assertThrows<ProfessorNotFound> { subjectService.updateSubject(10L, request) }
    }

    @Test
    fun `updateSubject should save and return updated response when data is valid`() {
        // Arrange
        val request = SubjectRequest(name = "Matematicas II", code = "MAT-102", professorId = 2L)
        val oldProfessor = Professor(id = 1L, name = "Old Prof")
        val newProfessor = Professor(id = 2L, name = "New Prof")
        val existingSubject = Subject(id = 10L, name = "Math", code = "MAT-101", professor = oldProfessor)
        val updatedSubject = Subject(id = 10L, name = "Matematicas II", code = "MAT-102", professor = newProfessor)

        `when`(subjectRepository.findById(10L)).thenReturn(Optional.of(existingSubject))
        `when`(professorRepository.findById(2L)).thenReturn(Optional.of(newProfessor))
        `when`(subjectRepository.save(any(Subject::class.java))).thenReturn(updatedSubject)

        // Act
        val response = subjectService.updateSubject(10L, request)

        // Assert
        assertEquals(10L, response.id)
        assertEquals("Matematicas II", response.name)
        assertEquals("New Prof", response.professor.name)
    }

    //TESTS: deleteSubject

    @Test
    fun `deleteSubject should throw SubjectNotFoundException when subject to delete does not exist`() {
        // Arrange
        `when`(subjectRepository.findById(10L)).thenReturn(Optional.empty())

        // Act & Assert
        assertThrows<SubjectNotFoundException> { subjectService.deleteSubject(10L) }
    }

    @Test
    fun `deleteSubject should call repository delete when subject exists`() {
        // Arrange
        val subject = Subject(id = 10L, name = "Matematicas", code = "MAT-101", professor = Professor())
        `when`(subjectRepository.findById(10L)).thenReturn(Optional.of(subject))

        // Act
        subjectService.deleteSubject(10L)

        // Assert
        verify(subjectRepository).delete(subject)
    }
}