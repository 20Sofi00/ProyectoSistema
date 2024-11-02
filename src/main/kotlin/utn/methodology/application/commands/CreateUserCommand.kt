package utn.methodology.application.commands

class CreateUserCommand(
    val name: String,
    val userName: String,
    val email: String,
    val password: String,
) {
    fun validate(): CreateUserCommand {
        checkNotNull(name) { throw IllegalArgumentException("name must be defined") }
        checkNotNull(userName) { throw IllegalArgumentException("userName must be defined") }
        checkNotNull(email) { throw IllegalArgumentException("email must be defined") }
        checkNotNull(password) { throw IllegalArgumentException("Contraseña must be defined") }

        if (!validName(name)) {
            throw IllegalArgumentException("statusCode 400 / BadRequest: Nombre no valido")
        }
        if (!validUserName(userName)) {
            throw IllegalArgumentException("statusCode 400 / BadRequest: Nombre de Usuario no valido")
        }
        if (!validEmail(email)) {
            throw IllegalArgumentException("statusCode 400 / BadRequest: Correo electrónico no valido")
        }
        if (!validPassword(password)) {
            throw IllegalArgumentException("statusCode 400 / BadRequest: Contraseña no valida")
        }

        return this
    }

    private fun validName(name: String): Boolean {
        val regex = ("^[a-zA-Z0-9._-]{3,15}$")
        return regex.toRegex().matches(name)
    }

    private fun validUserName(userName: String): Boolean {
        val regex = ("^[A-Za-z]{1}([A-Za-z]{2}|[0-9]{2}|[A-Za-z][0-9])([0-9]{4}){1}")
        return regex.toRegex().matches(userName)
    }

    private fun validEmail(email: String): Boolean {
        val regex = ("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
        return regex.toRegex().matches(email)
    }

    private fun validPassword(password: String): Boolean {
        val regex = ("^(?=.[a-z])(?=.[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")
        return regex.toRegex().matches(password)
    }
}