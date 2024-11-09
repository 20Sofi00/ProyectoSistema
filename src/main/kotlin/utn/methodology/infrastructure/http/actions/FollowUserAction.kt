package utn.methodology.infrastructure.http.actions
import utn.methodology.domain.entities.models.User
import utn.methodology.application.commands.FollowUserCommand
import utn.methodology.domain.entities.contracts.UserRepository


class FollowUserAction (private val userRepository: UserRepository)  {

        fun execute(command: FollowUserCommand): Result<String> {
            val follower = userRepository.findById(command.userId)
                ?: return Result.failure(Exception("El usuario que intenta seguir no existe"))

            val followed = userRepository.findById(command.followedId)
                ?: return Result.failure(Exception("El usuario a seguir no existe"))

            // Validar si el usuario ya sigue al otro
            if (follower.followed.contains(command.followedId)) {
                return Result.failure(Exception("Ya sigues a este usuario"))
            }

            followed.followers.add(follower.uuid)

            follower.followed.add(followed.uuid)


            userRepository.save(follower)
            userRepository.save(followed)


            return Result.success("Ahora sigues a ${followed.userName}")
        }
    }
