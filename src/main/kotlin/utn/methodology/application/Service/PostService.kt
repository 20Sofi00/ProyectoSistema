package utn.methodology.application.services

import utn.methodology.infrastructure.persistence.repositories.UserMongoRepository
import utn.methodology.infrastructure.persistence.repositories.MongoPostRepository
import utn.methodology.domain.entities.models.Post

class PostService(
    private val UserMongoRepository: UserMongoRepository,
    private val MongoPostRepository: MongoPostRepository
) {
    fun getPostsByFollowedUsers(userId: String): List<Post> {
        val followedUserIds = UserMongoRepository.getFollowedUserIds(userId)
        return MongoPostRepository.getPostsByUsers(followedUserIds)

    }
    fun getPostById(postId: String): Post? {
        return MongoPostRepository.findById(postId)
    }
    fun createPost(post: Post): Boolean {
        // Verifica si el mensaje está vacío
        if (post.message.isEmpty()) {
            throw IllegalArgumentException("El mensaje no puede estar vacío")
        }

        // Intenta guardar el post y devuelve el resultado
        return MongoPostRepository.save(post)
    }
}
}