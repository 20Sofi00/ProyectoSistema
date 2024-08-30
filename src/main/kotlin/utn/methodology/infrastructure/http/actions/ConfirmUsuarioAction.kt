package utn.methodology.infrastructure.http.actions
import utn.methodology.application.commandHandlers.ConfirmUsuarioHandler
import utn.methodology.application.commands.ConfirmUsuarioCommand
import utn.methodology.domain.entities.usuario

class ConfirmUsuarioAction(
    private val handler: ConfirmUsuarioHandler
) {
    fun execute(body: ConfirmUsuarioCommand) {


        body.validate().let {
            handler.handle(it)
        }

    }
}