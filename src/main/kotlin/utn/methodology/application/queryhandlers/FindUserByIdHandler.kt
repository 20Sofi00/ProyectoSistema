package utn.methodology.application.queryhandlers

import io.ktor.server.plugins.*
import utn.methodology.application.queries.FindUserByIdQuery
import utn.methodology.infrastructure.persistence.repositories.UserMongoRepository

class FindUserByIdHandler(
    private val userRepository: UserMongoRepository
) {

    fun handle(query: FindUserByIdQuery): Map<String, String> {

        val user = userRepository.findOne(query.id)

        if (user == null) {
            throw NotFoundException("user with id: ${query.id} not found")
        }

        return user.toPrimitives()
    }
}

