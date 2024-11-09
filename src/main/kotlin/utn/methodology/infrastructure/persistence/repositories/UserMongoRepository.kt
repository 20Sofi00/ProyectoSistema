package utn.methodology.infrastructure.persistence.repositories

import org.litote.kmongo.addToSet
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.UpdateOptions
import org.bson.Document
import org.litote.kmongo.updateOneById
import utn.methodology.domain.entities.contracts.UserRepository
import utn.methodology.domain.entities.models.User

class UserMongoRepository(private val database: MongoDatabase) : UserRepository {

    private val collection: MongoCollection<Document> = database.getCollection("users")

    fun getFollowedUserIds(userId: String): List<String> {
        val followersCollection = database.getCollection("followers")
        return followersCollection.find(Document("followerId", userId))
            .map { it.getString("followedId") }
            .toList()
    }
    fun followUser(followerId: String, followeeId: String): Boolean {
        val follower = findById(followerId)
        val followee = findById(followeeId)

        if (follower != null && followee != null && followerId != followeeId) {
            collection.updateOneById(
                followerId,
                addToSet(User::followed, followeeId) // Cambiado a followed
            )
            collection.updateOneById(
                followeeId,
                addToSet(User::followers, followerId)
            )
            return true
        }
        return false
    }

    override fun save(user: User) {
        println("UserMongoRepository - Saving user: $user")
        val options = UpdateOptions().upsert(true)

        val filter = Document("_id", user.getId()) // Usa el campo id como filtro
        val update = Document("\$set", user.toPrimitives())

        collection.updateOne(filter, update, options)
    }

    override fun findOne(id: String): User? {
        val filter = Document("_id", id)

        val primitives = collection.find(filter).firstOrNull()

        if (primitives == null) {
            return null
        }

        return User.fromPrimitives(primitives.toMap())
    }

    override fun findByName(name: String): User? {
        val filter = Document("userName", name)

        val primitives = collection.find(filter).firstOrNull()

        if (primitives == null) {
            return null
        }

        return User.fromPrimitives(primitives.toMap())
    }

    // Implementación del método findById
    override fun findById(followerId: String): User? {
        return findOne(followerId)
    }
}

