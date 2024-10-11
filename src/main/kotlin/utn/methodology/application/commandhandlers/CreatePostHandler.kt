package utn.methodology.application.commandhandlers

import utn.methodology.application.queries.FindPostByIdQuery
import utn.methodology.domain.entities.models.Post
import utn.methodology.application.commandhandlers.CreatePostHandler.*
import utn.methodology.application.commands.CreatePostCommand
import utn.methodology.infrastructure.persistence.repositories.MongoPostRepository
import java.time.LocalDateTime

class CreatePostHandler(private val postRepository: MongoPostRepository) {

    fun handle(command: CreatePostCommand): Post {
        // Validación del mensaje
        if (command.message.length > 280) {
            throw IllegalArgumentException("El mensaje excede el límite de caracteres")
        }

        // Validación del userId (por si necesitas asegurarte de que no esté vacío)
        if (command.userId.isBlank()) {
            throw IllegalArgumentException("El userId no puede estar vacío")
        }

        // Creación del post con la fecha actual
        val post = Post(
            userId = command.userId,
            message = command.message,
            content = command.content,
            createdAt = LocalDateTime.now()  // Usamos LocalDateTime para obtener la fecha actual,

        )
        postRepository.save(post)

        // Intentamos guardar el post en el repositorio
        try {
            postRepository.save(post)
        } catch (e: Exception) {
            throw RuntimeException("Error al guardar el post: ${e.message}")
        }
        return post
    }
}

