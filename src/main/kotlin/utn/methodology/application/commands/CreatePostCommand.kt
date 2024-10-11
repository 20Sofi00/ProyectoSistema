package utn.methodology.application.commands

import java.time.LocalDateTime

data class CreatePostCommand(

    val userId: String,
    val message: String,
    val  createdAt: LocalDateTime,
    val content: String,
)

