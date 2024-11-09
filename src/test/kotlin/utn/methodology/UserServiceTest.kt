package utn.methodology.infrastructure.persistence.repositories


import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class UserServiceTest {

    @Test
    fun create_user_should_returns_201() = testApplication {
        // Simula una solicitud HTTP POST para crear un usuario válido
        val response = client.post("/users") {
            contentType(ContentType.Application.Json)
            setBody("""
                {
                    "name": "Miguelita",
                    "userName": "Miguelita_NoEx",
                    "email": "miguelita-NoExiste@example.com",
                    "password": "Incorrecta.123"
                }
            """.trimIndent())
        }
        // Verifica que la respuesta sea 201 Created
        assertEquals(HttpStatusCode.Created, response.status)
    }

    @Test
    fun create_user_should_returns_400() = testApplication {
        // Simula una solicitud HTTP POST con datos incorrectos (ej: falta el email)
        val response = client.post("/users") {
            contentType(ContentType.Application.Json)
            setBody("""
                {
                    "name": "Josesito",
                    "userName": "Josesito_NoEx",
                    "password": "Incorrecta.123"
                }
            """.trimIndent())
        }
        // Verifica que la respuesta sea 400 Bad Request
        assertEquals(HttpStatusCode.BadRequest, response.status)
    }
}