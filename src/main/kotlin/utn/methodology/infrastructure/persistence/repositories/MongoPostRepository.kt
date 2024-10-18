package utn.methodology.infrastructure.persistence.repositories

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
// import com.mongodb.client.model.Filters
import org.bson.Document
import utn.methodology.domain.entities.models.Post
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
   /*
      fun findById(postId: String): Post? {
          val document = collection.find(Filters.eq("_id", postId)).first()
          return document?.let {
              Post(
                  userId = it.getString("userId"),
                  message = it.getString("message"),
                  createdAt = LocalDateTime.parse(it.getString("createdAt"))
              )
          }
      }

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
    fun delete(postId: String,userId:String) {
        // crea un filtro donde tanto el postId como el userId deben coincidir. Esto asegura que solo el autor del post pueda eliminarlo.
        val filter = Document("_id", postId).append("userId", userId);

        val result = collection.deleteOne(filter)
        if (result.deletedCount == 0L) {
            throw IllegalStateException("No se pudo eliminar el post con id: $postId para el usuario con id: $userId")
        }
    }
}
