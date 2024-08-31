package utn.methodology.infrastructure.http.router

import utn.methodology.domain.entities.usuario
import utn.methodology.infrastructure.http.actions.ConfirmUsuarioAction
import utn.methodology.infrastructure.persistence.connectToMongoDB
import utn.methodology.infrastructure.persistence.usuarioMongoRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.mongodb.client.*


fun Application.routear() {
    val mongoDatabase = connectToMongoDB() // Conexión a la base de datos

    val usuarioMongoRepository = usuarioMongoRepository(mongoDatabase) // Inyección del repositorio

    val confirmUserAction =
        confirmUsuarioAction(ConfirmUsuarioHandler(usuarioMongoRepository, eventBus)) // Inyección del manejador de la acción

//    val findUserByIdAction = FindUserByIdAction(FindUserByIdHandler(userMongoUserRepository))

    routing {

        post("/usuario") {
            println("Received POST request to /usuario")

            val body = call.receive<ConfirmUsuarioCommand>()

            ConfirmUsuarioAction.execute(body)

            call.respond(HttpStatusCode.Created, mapOf("message" to "ok"))
        }
    }
}