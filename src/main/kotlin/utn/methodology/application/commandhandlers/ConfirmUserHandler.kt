package utn.methodology.application.commandhandlers
import utn.methodology.domain.entities.models.User
import utn.methodology.application.commands.ConfirmUserCommand
import utn.methodology.domain.entities.contracts.UserRepository
import utn.methodology.infrastructure.persistence.repositories.UserMongoRepository
import java.util.*

class ConfirmUserHandler(
    private val userRepository: UserRepository,
) {
    fun handle(command: ConfirmUserCommand) {

    try {
        val user = User.create(
            command.name,
            command.userName,
            command.email,
            command.password,

            )

        userRepository.save(user)

    } catch (e: Exception) {

        throw RuntimeException("Error al crear el usuario: ${e.message}", e)
    }


        if (command.name.isBlank() || command.userName.isBlank() || command.email.isBlank() || command.password.isBlank()) {
            throw IllegalArgumentException("Todos los campos son obligatorios.")
        }




    }


}