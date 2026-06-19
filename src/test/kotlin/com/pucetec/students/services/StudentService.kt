package com.pucetec.students.services
import com.pucetec.students.dto.StudentRequest
import com.pucetec.students.entities.Student
import com.pucetec.students.exceptions.StudentNotFoundException
import com.pucetec.students.repositories.StudentRepository
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.util.Optional
import kotlin.test.Test
import kotlin.test.assertEquals

//mokito libreria para realizar los mocks (datos falsos de prueba) con JInit
@ExtendWith(MockitoExtension::class)
class StudentServiceTest {
    //lateinit permite inicializar un constructor no nulo.
    @Mock  //crea un objeti ofalso del repositorio, no se toca la bd real solo simulamos respuesta para test
    private lateinit var studentRepository : StudentRepository

    @InjectMocks //crea una instancia real del servicio, se va a inyectar todosl los mocks declarados @Mock
    private lateinit var studentService : StudentService

    @Test

    fun  `createStudent should throw a name BlankNameException when name is blank` (){
        //arrange: arregla el comportamiento del servicio prepara

        val request: StudentRequest = StudentRequest(name="", email = "text@test.com")

        //act:


        //assert
        assertThrows<BlankNameException>{
            studentService.createStudent(request)
        }
    }

    @Test
    fun`createStudent should return a valid response when name is valid` (){

        val request: StudentRequest = StudentRequest(name="Test", email = "test.com")

        val newStudent : Student= Student(
            id=1L,
            name=request.name,
            email=request.email
        )

        `when`(studentRepository.save(
            any(Student::class.java)))
            .thenReturn(newStudent)


        val response = studentService.createStudent(request)

        assertEquals(1L, response.id)
        assertEquals(request.name, response.name)
        assertEquals(request.email, response.email)
    }

    @Test
    fun `getAllStudent should return an empty list when there are 0 students` (){
        val students = emptyList<Student>()

        `when`(studentRepository.findAll()).thenReturn(students)

        val result = studentService.getAllStudents()

        assertEquals(0, result.size)
    }

    @Test
    fun `getAllStudent should return a list of students when there are registered students` (){
        val students = listOf(
            Student( id = 1L, name = "Test", email = "test.com"),
            Student( id = 1L, name = "Test1", email = "test1.com"),
        )
        `when`(studentRepository.findAll()).thenReturn(students)

        val result = studentService.getAllStudents()

        assertEquals(2, result.size)
        assertEquals(students.size, result.size)
        assertEquals("test", result[0].name)
    }

    @Test
    fun `GetStudentById should throw StudentNotFoundException when student is not found` (){

        //prepare
        `when`(studentRepository.findById(any(Long::class.java))).thenReturn(Optional.empty())
        //act


        //assert
        assertThrows <StudentNotFoundException>{ studentService.getStudentById(67) }

    }

    @Test
    fun `GetStudentById should return a valid response when student is present in DB` (){

        val studentId=12312312321

        //prepare
        `when`(studentRepository.findById(any(Long::class.java)))
            .thenReturn(Optional.empty())
        //act
        val result = studentService.getStudentById(67)

        //refactor es reesctructurar el codigo
        //assert
        assertEquals(studentId, result.id)






    }

}