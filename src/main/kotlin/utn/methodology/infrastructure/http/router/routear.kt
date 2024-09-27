package utn.methodology.infrastructure.http.router

import utn.methodology.infrastructure.http.actions.ConfirmUsuarioAction
import utn.methodology.infrastructure.persistence.connectToMongoDB
import utn.methodology.application.commandhandlers.ConfirmUserHandler
import utn.methodology.application.commands.ConfirmUserCommand
import utn.methodology.infrastructure.persistence.repositories.UsuarioMongoRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.mongodb.client.*
import io.ktor.server.request.*
import utn.methodology.infrastructure.http.actions.FindUsuariobyNombreAction


fun Application.routear() {
    val mongoDatabase = connectToMongoDB()

    val usuarioMongoRepository = UsuarioMongoRepository(mongoDatabase)

    val confirmUserAction =
        ConfirmUsuarioAction(ConfirmUserHandler(usuarioMongoRepository))

//    val findUserByIdAction = FindUserByIdAction(FindUserByIdHandler(userMongoUserRepository))

    routing {

        post("/usuario") {
            println("Received POST request to /usuario")

            val body = call.receive<ConfirmUserCommand>()

            confirmUserAction.execute(body)

            call.respond(HttpStatusCode.Created, mapOf("message" to "ok"))

        }

    }
}