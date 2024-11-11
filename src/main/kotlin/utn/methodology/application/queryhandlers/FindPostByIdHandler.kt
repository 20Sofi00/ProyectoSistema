package utn.methodology.application.queryhandlers;

import io.ktor.server.plugins.*;
import java.util.UUID
import utn.methodology.application.commands.CreatePostCommand
import utn.methodology.application.queries.FindPostByIdQuery;
import utn.methodology.domain.entities.models.Post;
import utn.methodology.infrastructure.persistence.repositories.MongoPostRepository;
import utn.methodology.infrastructure.persistence.repositories.UserMongoRepository;
import java.time.LocalDateTime


class FindPostByIdHandler(
    private val postRepository: MongoPostRepository,
    private val userRepository: UserMongoRepository
) {

    fun handle(command: CreatePostCommand): String {
        if (!userRepository.existsByUuid(command.userId)) {
            return "Error: debe existir el usuario."
        }

        // Validación de longitud del mensaje
        if (command.message.length > 500) {
            throw IllegalArgumentException("ERROR: El mensaje no puede exceder los 500 caracteres")
        }

        // Creación del post con la fecha y hora actuales
        val post = Post(
            id = UUID.randomUUID().toString(),
            userId = command.userId,
            message = command.message,
            createdAt = LocalDateTime.now()
        )

        return try {
            postRepository.save(post)
            "Post creado exitosamente"
        } catch (e: Exception) {
            "Error al crear el post: ${e.message}"
        }
    }
}