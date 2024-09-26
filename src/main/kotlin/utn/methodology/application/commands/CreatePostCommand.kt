package utn.methodology.application.commands

data class CreatePostCommand(
    val userId: String,
    val message: String
)

