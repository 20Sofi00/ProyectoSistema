package utn.methodology.application.commandhandlers

import utn.methodology.application.commands.DeletePostCommand
import utn.methodology.infrastructure.persistence.repositories.MongoPostRepository
import io.ktor.server.plugins.*

class DeletePostHandler(
    private val postRepository : MongoPostRepository
) {

    fun handle(command: DeletePostCommand): String {

        val post = postRepository.findOne(command.postId)

        if (post != null) {
            postRepository.delete(command.postId, command.userId);
            return "Se pudo eliminar correctamente"
        }
        throw NotFoundException("No se encontro el usuario con el id: ${command.postId}");



    }

}
