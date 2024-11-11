package utn.methodology.application.commands
import kotlinx.serialization.Serializable

@Serializable

data class CreatePostCommand(
    val userId: String,
    val message: String
)
{
    fun validate(): CreatePostCommand {
        checkNotNull(userId) { throw IllegalArgumentException("ERROR: Ingrese un id de usuario correcto") }
        checkNotNull(message) { throw IllegalArgumentException("ERROR: Debe ingresar un mensaje") }
        if (message.length > 500) {
            throw IllegalArgumentException("ERROR: El mensaje no puede superar los 500 caracteres.")
        }

        return this
    }
}
