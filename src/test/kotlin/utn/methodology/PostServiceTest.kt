package utn.methodology

import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class PostServiceTest {

    @Test
    fun create_post_should_returns_201() = testApplication {
        // Simula una solicitud HTTP POST para crear un post válido
        val response = client.post("/posts") {
            contentType(ContentType.Application.Json)
            setBody("""
                {
                    "userId": "1",
                    "message": "Este post no existe",
                    "createdAt": "2024-10-18T15:30:00"
                }
            """.trimIndent())
        }
        // Verifica que la respuesta sea 201 Created
        assertEquals(HttpStatusCode.Created, response.status)
    }

    @Test
    fun create_post_should_returns_400() = testApplication {
        // Simula una solicitud HTTP POST con datos incorrectos (ej: falta userId)
        val response = client.post("/posts") {
            contentType(ContentType.Application.Json)
            setBody("""
                {
                    "message": "Este post tampoco existe",
                    "createdAt": "2024-10-18T15:30:00"
                }
            """.trimIndent())
        }
        // Verifica que la respuesta sea 400 Bad Request
        assertEquals(HttpStatusCode.BadRequest, response.status)
    }
}