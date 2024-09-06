package utn.methodology.application.commandHandlers

import utn.methodology.application.commands.ConfirmUsuarioCommand
import utn.methodology.domain.contracts.UsuarioRepository
import utn.methodology.domain.entities.usuario
import utn.methodology.infrastructure.persistance.repositories.UsuarioMongoRepository
import java.util.*

class ConfirmUsuarioHandler(
    private val usuarioRepository: UsuarioRepository,
) {
    fun handle(command: ConfirmUsuarioCommand) {

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

        if (usuarioRepository.existsByNombreUsuario(command.nombreUsuario)) {
            throw IllegalArgumentException("El nombre de usuario ya existe.")
        }

        if (usuarioRepository.existsByCorreoElectronico(command.correoElectronico)) {
            throw IllegalArgumentException("El correo electrónico ya está registrado.")
        }

    }


}