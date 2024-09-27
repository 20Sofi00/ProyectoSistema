package utn.methodology.infrastructure.persistence.repositories

import io.ktor.utils.io.core.*
import org.bson.Document
import utn.methodology.domain.entities.Usuario
import utn.methodology.infrastructure.http.router.Postrouter
import utn.methodology.infrastructure.persistence.connectToMongoDB
import utn.methodology.infrastructure.persistence.repositories.UsuarioMongoRepository

class PostRepository {
    fun GetUserPosts(userId: String, order: String, limit: Int, offset: Int ): List<Post>{
        fun findOne(userId: String, order: String, limit: Int, offset: Int): Post? {

          val filter = Document("userId", userId)
          val PostDocuments = postCollection.find(filter).toList()
            return Usuario.fromPrimitives(primitives as Map<String, String>)
        }
    }
}