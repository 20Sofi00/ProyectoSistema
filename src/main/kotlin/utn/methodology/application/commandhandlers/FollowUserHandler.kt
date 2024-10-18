package utn.methodology.application.commandhandlers
import utn.methodology.application.commands.FollowUserCommand
import  utn.methodology.infrastructure.http.actions.FollowUserAction

class FollowUserHandler(private val followUserAction: FollowUserAction){
        fun handle(command: FollowUserCommand): Result<String> {
            return followUserAction.execute(command)
        }

}