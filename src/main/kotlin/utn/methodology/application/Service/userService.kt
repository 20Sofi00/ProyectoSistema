package utn.methodology.application.service

import utn.methodology.domain.entities.contracts.UserRepository
import utn.methodology.domain.entities.models.Post
import utn.methodology.domain.entities.models.User
import java.time.LocalDateTime

class UserService(private val userRepository: UserRepository) {

    fun createUser(name: String?, userName: String?, email: String?, password: String?, message: String?): Pair<Int, Post?> {
        // Validación básica de los campos del usuario y del post
        if (name.isNullOrBlank() || userName.isNullOrBlank() || email.isNullOrBlank() || password.isNullOrBlank() || message.isNullOrBlank()) {
            return Pair(400, null)
        }

        // Crear un nuevo usuario
        val user = User.create(name = name, userName = userName, email = email, password = password)

        // Guardar el usuario en el repositorio (simulado o real)
        userRepository.save(user)

        // Crear un post asociado al usuario
        val post = Post(
            userId = user.getId(),
            message = message,
            createdAt = LocalDateTime.now()
        )

        // Devolver el código 201 (creado) y el post creado
        return Pair(201, post)
    }
}