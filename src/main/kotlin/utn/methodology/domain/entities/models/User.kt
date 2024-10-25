package utn.methodology.domain.entities
import org.mindrot.jbcrypt.BCrypt
import java.util.UUID

class User (
    val uuid: String,
    val name: String,
    val userName: String,
    val email: String,
    val password: String,
    val followers: MutableList<String> = mutableListOf(),
    val followed: MutableList<String> = mutableListOf()
) {


    companion object {

        //Esta función tomará la contraseña como entrada y devolverá la contraseña cifrada
        fun hashPassword(plainPassword: String): String {
            return BCrypt.hashpw(plainPassword, BCrypt.gensalt())
        }
        fun fromPrimitives(primitives: Map<String, Any>): User {
            val uuid = (primitives["uuid"] as? String)
                ?: throw IllegalArgumentException("El id no puede ser nulo") // Cambié "id" a "uuid"
            val name = (primitives["name"] as? String)
                ?: throw IllegalArgumentException("El nombre no puede ser nulo")
            val userName = (primitives["userName"] as? String)
                ?: throw IllegalArgumentException("El nombre de usuario no puede ser nulo")
            val email = (primitives["email"] as? String)
                ?: throw IllegalArgumentException("El correo electrónico no puede ser nulo")
            val password = (primitives["password"] as? String)
                ?: throw IllegalArgumentException("La contraseña no puede ser nula")

            return User(uuid, name, userName, email, password)
        }


        fun create(

            name: String,
            userName: String,
            email: String,
            password: String,
        ): User {
            val hashedPassword = hashPassword(password)
            val user = User(
                UUID.randomUUID().toString(),
                name,
                userName,
                email,
                hashedPassword // Aquí almacenamos la contraseña cifrada
            )

            return user
        }
    }

    fun toPrimitives(): Map<String, Any?> {
        return mapOf(
            "id" to this.uuid,
            "name" to this.name,
            "userName" to this.userName,
            "email" to this.email,
            // No devolvemos la contraseña directamente por seguridad
        )
    }

    fun getId(): String {
        return this.uuid;
    }
}

