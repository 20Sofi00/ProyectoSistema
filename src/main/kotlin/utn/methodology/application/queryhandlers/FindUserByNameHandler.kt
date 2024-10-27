package utn.methodology.application.queryhandlers

import utn.methodology.application.queries.FindUserByNameQuery
import utn.methodology.infrastructure.persistence.repositories.UserMongoRepository
import io.ktor.server.plugins.*

class FindUserByNameHandler(
    private val userRepository: UserMongoRepository
) {

    fun handle(query: FindUserByNameQuery): Map<String, String> {

        val user = userRepository.findOne(query.name)

        if (user == null) {
            throw NotFoundException("usuario con el nombre: ${query.name} no fue encontrado")
        }

        return user.toPrimitives() as Map<String, String>
    }

}
