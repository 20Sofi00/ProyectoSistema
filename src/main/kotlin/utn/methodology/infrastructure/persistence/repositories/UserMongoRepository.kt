package utn.methodology.infrastructure.persistence.repositories

import org.litote.kmongo.addToSet
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.UpdateOptions
import utn.methodology.domain.entities.User
import org.bson.Document
import org.litote.kmongo.updateOneById
import utn.methodology.domain.entities.contracts.UserRepository

class UserMongoRepository(private val database: MongoDatabase) : UserRepository {

    private val collection: MongoCollection<Document> = database.getCollection("users")

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

        return User.fromPrimitives(primitives as Map<String, String>)
    }

    // Implementación del método findById
    override fun findById(followerId: String): User? {
        return findOne(followerId)
    }
}
