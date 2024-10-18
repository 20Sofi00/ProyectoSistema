package utn.methodology.application.queryhandlers

import utn.methodology.application.queries.FindUserByNameQuery
import utn.methodology.infrastructure.persistence.repositories.UsuarioMongoRepository
import io.ktor.server.plugins.*

class FindUserByNombreHandler(
    private val userRepository: UsuarioMongoRepository
) {

    fun handle(query: FindUserByNameQuery): Map<String, String> {

        val usuario = userRepository.findOne(query.nombre)

        if (usuario == null) {
            throw NotFoundException("usuario con el nombre: ${query.nombre} no fue encontrado")
        }

        return usuario.toPrimitives()
    }

}
