package utn.methodology.infrastructure.http.actions

import utn.methodology.application.commands.CreatePostCommand
import utn.methodology.application.commandhandlers.CreatePostHandler

class CreatePostAction(private val handler: CreatePostHandler) {
    fun execute(command: CreatePostCommand) {
        handler.handle(command)
    }
}
