package utn.methodology.infrastructure.http.actions

import utn.methodology.application.commands.CreatePostCommand
import utn.methodology.application.commandhandlers.CreatePostHandler

class CreatePostAction(private val handler: CreatePostHandler) {
    fun execute(body: CreatePostCommand) {
        body.validate().let {
            handler.handle(it)
    }
}
}
//Revisión de la Función CreatePostAction.execute: Asegúrate de que execute esté llamando al
// método validate de CreatePostCommand. Deberías también revisar si el CreatePostHandler.handle
//  está lanzando alguna excepción y si se está propagando correctamente.