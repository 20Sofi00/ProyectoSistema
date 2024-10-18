package utn.methodology.application.commands
import utn.methodology.domain.entities.models.Post

data class DeletePostCommand(
    val postId: Post,
    val userId: String
) {
    fun validate(): DeletePostCommand{
        checkNotNull(postId) {throw IllegalArgumentException("Id must be defined")}
        return this
    }
}
