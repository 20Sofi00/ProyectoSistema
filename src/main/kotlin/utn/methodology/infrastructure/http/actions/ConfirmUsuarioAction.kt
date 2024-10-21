package utn.methodology.infrastructure.http.actions
import utn.methodology.application.commandhandlers.ConfirmUserHandler
import utn.methodology.application.commands.ConfirmUserCommand
import  utn.methodology.infrastructure.http.router.routear


class ConfirmUsuarioAction(
    private val handler: ConfirmUserHandler
) {
    fun execute(body: ConfirmUserCommand) {


        body.validate().let {

            handler.handle(it)
        }

    }
}