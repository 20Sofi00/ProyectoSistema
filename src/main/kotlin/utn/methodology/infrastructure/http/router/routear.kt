package utn.methodology.infrastructure.http.router

import utn.methodology.infrastructure.http.actions.CreateUserAction
import utn.methodology.infrastructure.persistence.connectToMongoDB
import utn.methodology.application.commandhandlers.`CreateUserHandler.kt`
import utn.methodology.application.commands.`CreateUserCommand.kt`
import utn.methodology.infrastructure.persistence.repositories.UserMongoRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.request.*


fun Application.routear() {
    val mongoDatabase = connectToMongoDB()

    val userMongoRepository = UserMongoRepository(mongoDatabase)

    val confirmUserAction =
        CreateUserAction(`CreateUserHandler.kt`(userMongoRepository))

//    val findUserByIdAction = FindUserByIdAction(FindUserByIdHandler(userMongoUserRepository))

    routing {

        post("/user") {
            println("Received POST request to /user")

            val body = call.receive<`CreateUserCommand.kt`>()

            confirmUserAction.execute(body)

            call.respond(HttpStatusCode.Created, mapOf("message" to "ok"))
        }
    }
}