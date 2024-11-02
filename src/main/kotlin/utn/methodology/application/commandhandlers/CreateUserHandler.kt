package utn.methodology.application.commandhandlers

import utn.methodology.application.commands.CreateUserCommand
import utn.methodology.domain.entities.models.User
import utn.methodology.domain.entities.contracts.UserRepository

class CreateUserHandler(
    private val userRepository: UserRepository,
) {
    fun handle(command: CreateUserCommand) {
        // Validamos el comando antes de proceder
        command.validate()

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
    }
}