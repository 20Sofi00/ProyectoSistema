package utn.methodology.application.commandhandlers

import utn.methodology.application.commands.ConfirmUserCommand
import utn.methodology.domain.contracts.UsuarioRepository
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

        if (usuarioRepository.existsByNombreUsuario(command.nombreUsuario)) {
            throw IllegalArgumentException("El nombre de usuario ya existe.")
        }

        if (usuarioRepository.existsByCorreoElectronico(command.correoElectronico)) {
            throw IllegalArgumentException("El correo electrónico ya está registrado.")
        }

    }


}