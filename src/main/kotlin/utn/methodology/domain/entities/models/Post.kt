package utn.methodology.domain.entities.models


import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class Post(
    var id: String,
    var userId: String,
    var message: String,
    var createdAt: LocalDateTime
){
    companion object {
        fun fromPrimitives(primitives: Map<String, Any>): Post {
            val dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME
            val createdAt = LocalDateTime.parse(primitives["createdAt"] as String, dateTimeFormatter)
            val post = Post(
                primitives["id"] as String,
                primitives["userId"] as String,
                primitives["message"] as String,
                createdAt
            )
            return post
        }

    }


    fun update(id: String, userId: String, message: String, createdAt: LocalDateTime){
        this.id = id
        this.userId = userId
        this.message = message
        this.createdAt = createdAt
    }
    fun toPrimitives(): Map<String, Any> {
        return mapOf(
            "id" to this.id.toString(),
            "userId" to this.userId,
            "message" to this.message,
            "createdAt" to this.createdAt
        )
    }
    fun GetUserId(): String{ return  this.userId}

}


