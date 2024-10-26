package utn.methodology.infrastructure.http.router

import utn.methodology.infrastructure.http.actions.ConfirmUserAction
import utn.methodology.infrastructure.persistence.connectToMongoDB
import utn.methodology.application.commandhandlers.ConfirmUserHandler
import utn.methodology.application.commands.ConfirmUserCommand
import utn.methodology.infrastructure.persistence.repositories.UserMongoRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.mongodb.client.*
import io.ktor.server.request.*


fun Application.routear() {
    val mongoDatabase = connectToMongoDB()

    val userMongoRepository = UserMongoRepository(mongoDatabase)

    val confirmUserAction =
        ConfirmUserAction(ConfirmUserHandler(userMongoRepository))

//    val findUserByIdAction = FindUserByIdAction(FindUserByIdHandler(userMongoUserRepository))

    routing {

        post("/user") {
            println("Received POST request to /user")

            val body = call.receive<ConfirmUserCommand>()

            confirmUserAction.execute(body)

            call.respond(HttpStatusCode.Created, mapOf("message" to "ok"))
        }
    }
}