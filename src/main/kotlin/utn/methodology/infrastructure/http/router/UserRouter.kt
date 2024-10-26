package utn.methodology.infrastructure.http.router

import utn.methodology.application.commandhandlers.ConfirmUserHandler
import utn.methodology.application.commands.ConfirmUserCommand
import utn.methodology.infrastructure.http.actions.ConfirmUserAction
import utn.methodology.infrastructure.persistence.repositories.UserMongoRepository
import utn.methodology.domain.entities.contracts.UserRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import utn.methodology.infrastructure.persistence.connectToMongoDB

fun Application.userRoutes() {
    val mongoDatabase = connectToMongoDB() // Conexión a la base de datos

    val userRepository = UserMongoRepository(mongoDatabase) // Inyección del repositorio

    val confirmUserAction =
        ConfirmUserAction(ConfirmUserHandler(userRepository)) // Inyección del manejador de la acción



    routing {

        post("/users") {
            val body = call.receive<ConfirmUserCommand>()
            ConfirmUserAction.execute(body);

            call.respond(HttpStatusCode.Created, mapOf("message" to "ok"))
        }

        get("/users") {
            call.respond(HttpStatusCode.OK,  mapOf("message" to "ok"))
        }




    }

}