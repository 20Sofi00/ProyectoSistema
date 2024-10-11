package utn.methodology.domain.entities.models

import java.time.LocalDateTime

data class Post(
    val userId: String,
    val message: String,
    val createdAt: LocalDateTime,
    val content: String,
)
