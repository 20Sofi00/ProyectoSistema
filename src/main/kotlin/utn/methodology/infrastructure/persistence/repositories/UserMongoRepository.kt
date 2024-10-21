package utn.methodology.infrastructure.persistence.repositories

import org.litote.kmongo.addToSet
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.UpdateOptions
import utn.methodology.domain.entities.models.User
//import io.github.cdimascio.dotenv.dotenv
import org.bson.Document
import utn.methodology.domain.entities.contracts.UserRepository

class UserMongoRepository(private val database: MongoDatabase): UserRepository {

    private var collection: MongoCollection<Any>;

    init {
        collection = database.getCollection("users") as MongoCollection<Any>;
    }
    fun followUser(followerId: List<String>, followeeId: List<String>): Boolean {
        val follower = findById(followerId)
        val followee = findById(followeeId)

        if (follower != null && followee != null && followerId != followeeId) {
            collection.updateOneById(
                followerId,
                addToSet(User::following, followeeId)
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
        val options = UpdateOptions().upsert(true);

        val filter = Document("_id", user.getId()) // Usa el campo id como filter
        val update = Document("\$set", user.toPrimitives())

        collection.updateOne(filter, update, options)
    }


    override fun findOne(id: String): User? {
        val filter = Document("_id", id);

        val primitives = collection.find(filter).firstOrNull();

        if (primitives == null) {
            return null;
        }

        return User.fromPrimitives(primitives as Map<String, String>)
    }
}

