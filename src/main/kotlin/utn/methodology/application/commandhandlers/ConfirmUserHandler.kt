package utn.methodology.application.commandhandlers
import utn.methodology.domain.entities.Usuario
import utn.methodology.application.commands.ConfirmUserCommand
import utn.methodology.domain.entities.contracts.UsuarioRepository
import utn.methodology.infrastructure.persistence.repositories.MongoUserRepository
import java.util.*

class ConfirmUserHandler(
    private val usuarioRepository: UsuarioRepository,
) {
    fun handle(command: ConfirmUserCommand) {

       try {
        val usuario = Usuario.create(
            command.nombre,
            command.nombreUsuario,
            command.correoElectronico,
            command.contrasena,

            )

        usuarioRepository.save(usuario)

       } catch (e: Exception) {

           throw RuntimeException("Error al crear el usuario: ${e.message}", e)
       }


        if (command.nombre.isBlank() || command.nombreUsuario.isBlank() || command.correoElectronico.isBlank() || command.contrasena.isBlank()) {
            throw IllegalArgumentException("Todos los campos son obligatorios.")
        }




    }


}