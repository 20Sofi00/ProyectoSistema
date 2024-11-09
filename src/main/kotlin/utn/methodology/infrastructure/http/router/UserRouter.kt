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
import utn.methodology.application.queries.FindUserByNameQuery
import utn.methodology.application.queryhandlers.FindUserByIdHandler
import utn.methodology.application.queryhandlers.FindUserByNameHandler
import utn.methodology.infrastructure.http.actions.FindUserByIdAction
import utn.methodology.infrastructure.http.actions.FindUserByNameAction
import utn.methodology.infrastructure.persistence.connectToMongoDB

fun Application.userRoutes() {
    val mongoDatabase = connectToMongoDB() // Conexión a la base de datos

    val userRepository = UserMongoRepository(mongoDatabase) // Inyección del repositorio

    val createUserAction = CreateUserAction(CreateUserHandler(userRepository))

    val findUserByIdAction = FindUserByIdAction(FindUserByIdHandler(userRepository))

    val findUserByNameAction = FindUserByNameAction(FindUserByNameHandler(userRepository))



    routing {

        post("/users") {
            val body = call.receive<CreateUserCommand>()
            createUserAction.execute(body)
            call.respond(HttpStatusCode.Created, mapOf("message" to "Usuario creado exitosamente"))
        }

        get("/users") {

            val name = call.request.queryParameters["userName"].toString()

            val query = FindUserByNameQuery(name)

            val result = findUserByNameAction.execute(query)

            call.respond(HttpStatusCode.OK, result)
        }

        get("/users/{id}") {

            val query = FindUserByIdQuery(call.parameters["id"].toString())

            val result = findUserByIdAction.execute(query)

            call.respond(HttpStatusCode.OK, result)

        }

    }

}