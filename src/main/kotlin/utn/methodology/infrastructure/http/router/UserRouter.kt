package utn.methodology.infrastructure.http.router

import utn.methodology.application.commandhandlers.CreateUserHandler
import utn.methodology.application.commands.CreateUserCommand
import utn.methodology.infrastructure.http.actions.CreateUserAction
import utn.methodology.infrastructure.persistence.repositories.UserMongoRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import utn.methodology.application.queries.FindUserByIdQuery
import utn.methodology.application.queryhandlers.FindUserByIdHandler
import utn.methodology.infrastructure.http.actions.FindUserByIdAction
import utn.methodology.infrastructure.persistence.connectToMongoDB

fun Application.userRoutes() {
    val mongoDatabase = connectToMongoDB() // Conexión a la base de datos

    val userRepository = UserMongoRepository(mongoDatabase) // Inyección del repositorio

    val createUserAction = CreateUserAction(CreateUserHandler(userRepository))

    val findUserByIdAction = FindUserByIdAction(FindUserByIdHandler(userRepository))



    routing {

        post("/users") {
            val body = call.receive<CreateUserCommand>()
            createUserAction.execute(body)
            call.respond(HttpStatusCode.Created, mapOf("message" to "Usuario creado exitosamente"))
        }

        get("/users") {
            call.respond(HttpStatusCode.OK,  mapOf("message" to "ok"))
        }

        get("/users/{id}") {

            val query = FindUserByIdQuery(call.parameters["id"].toString())

            val result = findUserByIdAction.execute(query)

            call.respond(HttpStatusCode.OK, result)

        }

    }

}