package com.pucetec.students.services

import com.pucetec.students.dto.ProfessorRequest
import com.pucetec.students.entities.Professor
import com.pucetec.students.exceptions.ProfessorNotFound
import com.pucetec.students.repositories.ProfessorRepository
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
class ProfessorServiceTest {

    @Mock
    private lateinit var professorRepository: ProfessorRepository

    @InjectMocks
    private lateinit var professorService: ProfessorService

    // --- TESTS: createProfessor ---

    @Test
    fun `createProfessor should throw IllegalArgumentException when name is blank`() {
        // Arrange
        val request = ProfessorRequest(name = "", email = "prof@test.com")

        // Act & Assert
        assertThrows<IllegalArgumentException> { professorService.createProfessor(request) }
    }

    @Test
    fun `createProfessor should return ProfessorResponse when request is valid`() {
        // Arrange
        val request = ProfessorRequest(name = "Luis Carrasco", email = "luis@mail.com")
        val savedProfessor = Professor(id = 1L, name = "Luis Carrasco", email = "luis@mail.com")

        `when`(professorRepository.save(any(Professor::class.java))).thenReturn(savedProfessor)

        // Act
        val response = professorService.createProfessor(request)

        // Assert
        assertEquals(1L, response.id)
        assertEquals("Luis Carrasco", response.name)
    }

    // --- TESTS: getAllProfessors ---

    @Test
    fun `getAllProfessors should return list of professors`() {
        // Arrange
        val professors = listOf(Professor(id = 1L, name = "Luis Carrasco", email = "luis@mail.com"))
        `when`(professorRepository.findAll()).thenReturn(professors)

        // Act
        val result = professorService.getAllProfessors()

        // Assert
        assertEquals(1, result.size)
        assertEquals("Luis Carrasco", result[0].name)
    }

    // --- TESTS: getProfessorById ---

    @Test
    fun `getProfessorById should throw ProfessorNotFound when id does not exist`() {
        // Arrange
        `when`(professorRepository.findById(5L)).thenReturn(Optional.empty())

        // Act & Assert
        assertThrows<ProfessorNotFound> { professorService.getProfessorById(5L) }
    }

    @Test
    fun `getProfessorById should return ProfessorResponse when id exists`() {
        // Arrange
        val professor = Professor(id = 5L, name = "Luis Carrasco", email = "luis@mail.com")
        `when`(professorRepository.findById(5L)).thenReturn(Optional.of(professor))

        // Act
        val response = professorService.getProfessorById(5L)

        // Assert
        assertEquals(5L, response.id)
        assertEquals("Luis Carrasco", response.name)
    }

    // --- TESTS: updateProfessor ---

    @Test
    fun `updateProfessor should throw IllegalArgumentException when name is blank`() {
        // Arrange
        val request = ProfessorRequest(name = "  ", email = "test@test.com")

        // Act & Assert
        assertThrows<IllegalArgumentException> { professorService.updateProfessor(10L, request) }
    }

    @Test
    fun `updateProfessor should throw ProfessorNotFound when professor does not exist`() {
        // Arrange
        val request = ProfessorRequest(name = "Valid Name", email = "test@test.com")
        `when`(professorRepository.findById(10L)).thenReturn(Optional.empty())

        // Act & Assert
        assertThrows<ProfessorNotFound> { professorService.updateProfessor(10L, request) }
    }

    @Test
    fun `updateProfessor should save and return updated professor response`() {
        // Arrange
        val request = ProfessorRequest(name = "New Name", email = "new@test.com")
        val existing = Professor(id = 10L, name = "Old Name", email = "old@test.com")
        val updated = Professor(id = 10L, name = "New Name", email = "new@test.com")

        `when`(professorRepository.findById(10L)).thenReturn(Optional.of(existing))
        `when`(professorRepository.save(any(Professor::class.java))).thenReturn(updated)

        // Act
        val response = professorService.updateProfessor(10L, request)

        // Assert
        assertEquals(10L, response.id)
        assertEquals("New Name", response.name)
        assertEquals("new@test.com", response.email)
    }

    // --- TESTS: deleteProfessor ---

    @Test
    fun `deleteProfessor should throw ProfessorNotFound when id does not exist`() {
        // Arrange
        `when`(professorRepository.findById(8L)).thenReturn(Optional.empty())

        // Act & Assert
        assertThrows<ProfessorNotFound> { professorService.deleteProfessor(8L) }
    }

    @Test
    fun `deleteProfessor should call delete on repository when professor exists`() {
        // Arrange
        val professor = Professor(id = 8L, name = "Grace Bonilla", email = "grace@mail.com")
        `when`(professorRepository.findById(8L)).thenReturn(Optional.of(professor))

        // Act
        professorService.deleteProfessor(8L)

        // Assert
        verify(professorRepository).delete(professor)
    }
}