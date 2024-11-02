package utn.methodology

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import utn.methodology.application.services.PostService
import utn.methodology.domain.entities.models.Post
import utn.methodology.infrastructure.persistence.repositories.MongoPostRepository
import utn.methodology.infrastructure.persistence.repositories.UserMongoRepository
import java.time.LocalDateTime

class PostServiceTest {

    private val userMongoRepository: UserMongoRepository = mock(UserMongoRepository::class.java)
    private val mongoPostRepository: MongoPostRepository = mock(MongoPostRepository::class.java)
    private val postService = PostService(userMongoRepository, mongoPostRepository)

    @Test
    fun create_post_should_returns_201() {
        // Arrange
        val userId = "user123"
        val post = Post(id = "post123", userId = userId, message = "Hello, World!", createdAt = LocalDateTime.now())

        // Simular que el repositorio devuelve true al intentar guardar el post
        `when`(mongoPostRepository.save(post)).thenReturn(true)

        // Act
        val result = postService.createPost(post)

        // Assert
        assertEquals(true, result)
        verify(mongoPostRepository).save(post) // Verificar que el método save fue llamado
    }

    @Test
    fun create_post_should_returns_400() {
        // Arrange
        val userId = "user123"
        val post = Post(id = "post123", userId = userId, message = "", createdAt = LocalDateTime.now()) // Mensaje vacío

        // Act & Assert
        val exception = assertThrows<IllegalArgumentException> {
            postService.createPost(post)
        }
        assertEquals("El mensaje no puede estar vacío", exception.message) // Verificar que se lanza la excepción correcta
    }
}