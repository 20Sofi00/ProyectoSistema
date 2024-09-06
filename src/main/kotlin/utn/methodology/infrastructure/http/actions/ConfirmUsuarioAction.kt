package utn.methodology.infrastructure.http.actions
import utn.methodology.application.commandHandlers.ConfirmUserHandler
import utn.methodology.application.commands.ConfirmUsuarioCommand

class ConfirmUsuarioAction(
    private val handler: ConfirmUserHandler
) {
    fun execute(body: ConfirmUserCommand) {


        body.validate().let {
            handler.handle(it)
        }

    }
}