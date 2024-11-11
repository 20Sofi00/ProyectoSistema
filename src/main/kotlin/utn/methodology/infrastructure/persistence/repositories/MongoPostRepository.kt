package utn.methodology.infrastructure.persistence.repositories
import com.mongodb.client.model.Filters
import java.util.UUID
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase

import org.bson.Document
import utn.methodology.domain.entities.models.Post
import java.time.LocalDateTime
import java.time.ZoneId

class MongoPostRepository(private val database: MongoDatabase) {

    private val collection: MongoCollection<Document> = database.getCollection("posts")

    fun save(post: Post) {
        val document = Document()
            .append("userId", post.userId)
            .append("message", post.message)
            .append("createdAt", post.createdAt.toString())

        collection.insertOne(document)  // Guardar el documento en la colección
    }
    fun updateOneById(postId: String, updatedPost: Post): Boolean {
        val updateResult = collection.updateOne(
            Filters.eq("_id", postId),
            Document("\$set", Document("message", updatedPost.message))
                .append("createdAt", updatedPost.createdAt.toString())
        )
        return updateResult.modifiedCount > 0
    }


    fun findById(
        userId: String,
        order: String = "DESC",
        limit: Int? = null,
        offset: Int? = null
    ): List<Post> {
        val filter = Document("userId", userId)
        val sortOrder = if (order == "ASC") 1 else -1
        var query = collection.find(filter)
        if (offset != null) {
            query = query.skip(offset)
        }
        if (limit != null) {
            query = query.limit(limit)
        }
        query = query.sort(Document("createdAt", sortOrder))
        val primitives = query.toList()

        return primitives.map {
            Post.fromPrimitives(it as Map<String, Any>)
        }
    }


    fun findAll(): List<Post> {
        return collection.find().map {
            Post(
                id = UUID.randomUUID().toString(),
                userId = it.getString("userId"),
                message = it.getString("message"),
                createdAt = LocalDateTime.parse(it.getString("createdAt"))
            )
        }.toList()
    }
    fun findOne(id: Post): Post? {
        val filter = Document("_id", id)
        val primitives = collection.find(filter).firstOrNull()
        return primitives?.let { Post.fromPrimitives(it.toMap() as Map<String, String>) }
    }


    fun delete(postId: Post, userId: String) {
        val filter = Document("_id", postId).append("userId", userId)
        val result = collection.deleteOne(filter)
        if (result.deletedCount == 0L) {
            throw IllegalStateException("No se pudo eliminar el post con id: $postId para el usuario con id: $userId")
        }
    }

    fun getPostsByUsers(userIds: List<String>): List<Post> {
        return collection.find(Filters.`in`("userId", userIds))
            .map { doc: Document ->
                Post(
                    id = UUID.randomUUID().toString(),
                    userId = doc.getString("userId"),
                    message = doc.getString("message"),
                    createdAt = doc.getDate("createdAt")
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime()
                )
            }
            .toList()
    }
}

class UserRepository(private val database: MongoDatabase) {

    private val followersCollection: MongoCollection<Document> = database.getCollection("followers")

    fun getFollowedUserIds(userId: String): List<String> {
        return followersCollection.find(Document("followerId", userId))
            .map { it.getString("followedId") }
            .toList()
    }
}
