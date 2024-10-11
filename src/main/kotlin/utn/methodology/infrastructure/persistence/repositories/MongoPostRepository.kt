package utn.methodology.infrastructure.persistence.repositories

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
// import com.mongodb.client.model.Filters
import org.bson.Document
import utn.methodology.domain.entities.models.Post
import java.time.LocalDateTime
import java.time.ZoneOffset

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
    fun FindById(PostId: String, order: String, limit: Int, offset: Int): List<Post>{
        val orderBy = if (order == "ASC") 1 else -1

        return collection.find(Filters.eq("userId", PostId))
            .sort(Document("createdAt", orderBy))
            .skip(offset)
            .limit(limit)
            .map {
                Post(
                    userId = it.getString("userId"),
                    message = it.getString("message"),
                    createdAt = LocalDateTime.parse(it.getString("createdAt")),
                    content = it.getString("Content")
                )
            }.toList()

//        val document = collection.find(Filters.eq("_id", PostId)).first()
//        return document?.let {
//            return Post(userId = it.getString("userId"),
//                message = it.getString("message"),
//                createdAt = LocalDateTime.parse(it.getString("createdAt")),
//                content = it.getString("content"))
        }

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

