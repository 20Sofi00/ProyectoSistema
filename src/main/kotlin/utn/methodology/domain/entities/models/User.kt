package utn.methodology.domain.entities

import org.mindrot.jbcrypt.BCrypt
import java.util.UUID

class User (
    private val uuid: String,
    private val name: String,
    private val userName: String,
    private val email: String,
    private val password: String,
    val followers: MutableList<String> = mutableListOf(),
    val followed: MutableList<String> = mutableListOf()
) {


    companion object {

        //Esta función tomará la contraseña como entrada y devolverá la contraseña cifrada
        fun hashPassword(plainPassword: String): String {
            return BCrypt.hashpw(plainPassword, BCrypt.gensalt())
        }
        fun fromPrimitives(primitives: Map<String, Any>): User {

            // Verificamos cada valor con 'as?' si el valor no es del tipo correcto devuelve null
            // Luego consulta con ':?' , si el valor a la izquierda es null lanzamos una excepción personalizada.
                val uuid = (primitives["id"] as? String)
                    ?: throw IllegalArgumentException("El id no puede ser nulo")
                val name = (primitives["name"] as? String)
                    ?: throw IllegalArgumentException("El nombre no puede ser nulo")
                val userName = (primitives["userName"] as? String)
                    ?: throw IllegalArgumentException("El nombre de usuario no puede ser nulo")
                val email = (primitives["email"] as? String)
                    ?: throw IllegalArgumentException("El correo electrónico no puede ser nulo")
                val password = (primitives["password"] as? String)
                    ?: throw IllegalArgumentException("La contraseña no puede ser nula")

            return User(uuid, name, userName, email, password);


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