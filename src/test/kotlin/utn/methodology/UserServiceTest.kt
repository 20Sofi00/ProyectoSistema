package utn.methodology

import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.*

class UserServiceTest {

    private val postService = PostService()

    @Test
    fun create_user_should_returns_201() {
        // Simulación de datos válidos
        val name = "John Doe"
        val email = "john.doe@example.com"

        // Ejecutamos la función que crea el usuario
        val (statusCode, user) = postService.createUser(name, email)

        // Verificamos que el status sea 201
        assertEquals(201, statusCode)
        assertNotNull(user)
        assertEquals("John Doe", user?.name)
        assertEquals("john.doe@example.com", user?.email)
    }

    @Test
    fun create_user_should_returns_400() {
        // Simulación de datos inválidos (nombre nulo)
        val name: String? = null
        val email = "invalid@example.com"

        // Ejecutamos la función con un nombre inválido
        val (statusCode, user) = postService.createUser(name, email)

        // Verificamos que el status sea 400 y que no se haya creado el usuario
        assertEquals(400, statusCode)
        assertNull(user)
    }

    @Test
    fun create_user_should_returns_400_for_empty_email() {
        // Simulación de datos inválidos (email vacío)
        val name = "Valid Name"
        val email = ""

        // Ejecutamos la función con un email vacío
        val (statusCode, user) = postService.createUser(name, email)

        // Verificamos que el status sea 400 y que no se haya creado el usuario
        assertEquals(400, statusCode)
        assertNull(user)
    }
}
