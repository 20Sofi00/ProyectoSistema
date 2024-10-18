package utn.methodology.infrastructure.http.actions
import utn.methodology.application.commands.FollowUserCommand
import utn.methodology.domain.entities.contracts.UsuarioRepository


class FollowUserAction (private val userRepository: UsuarioRepository)  {

        fun execute(command: FollowUserCommand): Result<String> {
            val follower = userRepository.findById(command.userId)
                ?: return Result.failure(Exception("El usuario que intenta seguir no existe"))

            val followed = userRepository.findById(command.followedId)
                ?: return Result.failure(Exception("El usuario a seguir no existe"))

            // Validar si el usuario ya sigue al otro
            if (follower.followed.contains(followed.uuid)) {
                return Result.failure(Exception("Ya sigues a este usuario"))
            }

            val updatedFollowed = followed.copy(
                followers = followed.followers + follower.uuid  // Crea una nueva lista con el nuevo seguidor
            )

            // 6. Guardar los cambios en ambos usuarios
            userRepository.save(updatedFollower)
            userRepository.save(updatedFollowed)

            // 7. Devolver un resultado de éxito
            return Result.success("Ahora sigues a ${followed.nombreUsuario}")
        }
    }
