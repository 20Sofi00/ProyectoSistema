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
    fun getPostById(postId: String): List<Post> {
        return MongoPostRepository.findById(postId)
    }
}