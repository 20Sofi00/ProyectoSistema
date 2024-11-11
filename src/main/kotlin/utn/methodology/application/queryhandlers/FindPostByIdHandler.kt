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

    fun handle(query: FindPostByIdQuery): List<Post> {
        if (!userRepository.existsByUuid(query.id)) {
            return emptyList()
        }


        // Post viejo actualizado
//        val post = Post(
//            id = UUID.randomUUID().toString(),
//            userId = command.userId,
//            message = command.message,
//            createdAt = LocalDateTime.now()
//        )

        val posts = postRepository.findById(
            query.id,
            query.order,
            query.limit,
            query.offset)

        if (posts.isEmpty()) {
            return emptyList()
        }
        return posts
    }
}