package utn.methodology.infrastructure.persistence.repositories
import com.mongodb.client.model.Filters

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
// import com.mongodb.client.model.Filters
import org.bson.Document
import utn.methodology.domain.entities.models.Post
import java.time.LocalDateTime
import java.time.ZoneId

// import java.time.LocalDateTime

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

fun findById(postId: String): Post? {
        val document = collection.find(Filters.eq("_id", postId)).first()
        return document?.let {
            Post(
                id = postId,
                userId = it.getString("userId"),
                message = it.getString("message"),
                createdAt = LocalDateTime.parse(it.getString("createdAt"))
            )


        }
    }
    fun getPostsByUsers(userIds: List<String>): List<Post> {
        return collection.find(Filters.`in`("userId", userIds))
            .map { doc: Document ->
                Post(
                    id = doc.getObjectId("_id").toHexString(),
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



    /*
        fun findAll(): List<Post> {
            return collection.find().map {
                Post(
                    userId = it.getString("userId"),
                    message = it.getString("message"),
                    createdAt = LocalDateTime.parse(it.getString("createdAt"))
                )
            }.toList()
        }
          */
}
class UserRepository(private val database: MongoDatabase) {

    private val followersCollection: MongoCollection<Document> = database.getCollection("followers")

    fun getFollowedUserIds(userId: String): List<String> {
        return followersCollection.find(Document("followerId", userId))
            .map { it.getString("followedId") }
            .toList()
    }
}
