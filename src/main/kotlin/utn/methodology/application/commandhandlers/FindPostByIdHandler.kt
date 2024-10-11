package utn.methodology.application.commandhandlers

import utn.methodology.domain.entities.models.Post
import utn.methodology.application.queries.FindPostByIdQuery
import utn.methodology.infrastructure.persistence.repositories.MongoPostRepository
import java.time.LocalDateTime

//class FindPostByIdHandler( val postRepository: MongoPostRepository) {
//        fun handle(query: FindPostByIdQuery): List<Post> {
//        // Buscar el post en el repositorio usando el ID de la consulta
//            query.Validate() //Valida los parametros antes de continuar
//         return postRepository.FindById(query.id, query.order, query.limit, query.offset)
//        }
//    }
class FindPostByIdHandler(private val postRepository: MongoPostRepository) {

    fun handle(query: FindPostByIdQuery): List<Post>{
        query.Validate()
        return postRepository.FindById(query.id,query.order,query.limit,query.offset)
    }
}



