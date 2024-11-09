package utn.methodology.application.commandhandlers

import utn.methodology.application.commands.DeletePostCommand
import utn.methodology.infrastructure.persistence.repositories.MongoPostRepository
import io.ktor.server.plugins.*

class DeletePostHandler(
    private val postRepository : MongoPostRepository
) {

    fun handle(command: DeletePostCommand) {

        val post = postRepository.findById(command.postId.toString())

        if (post != null) {
            postRepository.delete(command.postId, command.userId);
            return
        }
        throw NotFoundException("not found user with id: ${command.postId}")
    }
}