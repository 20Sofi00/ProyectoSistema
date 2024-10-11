package utn.methodology.infrastructure.http.router

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import utn.methodology.application.commands.CreatePostCommand
import utn.methodology.application.commandhandlers.CreatePostHandler
import utn.methodology.application.commandhandlers.FindPostByIdHandler
import utn.methodology.infrastructure.http.actions.CreatePostAction
import utn.methodology.infrastructure.persistence.connectToMongoDB
import utn.methodology.application.queries.FindPostByIdQuery
import utn.methodology.infrastructure.persistence.repositories.MongoPostRepository

fun main() {
    embeddedServer(Netty, port = 8080, module = Application::module).start(wait = true)
}
fun Application.module() {
    // Conectar a la base de datos MongoDB
    val mongoDatabase = connectToMongoDB()

    // Crear el repositorio de posts
    val postRepository = MongoPostRepository(mongoDatabase)

    // Crear el handler y la acción para crear posts
    val createPostHandler = CreatePostHandler(postRepository)
    val createPostAction = CreatePostAction(createPostHandler)
    // Crear la instancia del handler
    val findPostByIdHandler = FindPostByIdHandler(postRepository)



    routing {
        post("/posts") {
            val body = call.receive<CreatePostCommand>()
            createPostAction.execute(body)
            call.respond(HttpStatusCode.Created, mapOf("message" to "Post creado exitosamente"))
        }
        get("/posts") {
            val postId = call.parameters["id"]?: return@get call.respondText ("Falta Id", status = HttpStatusCode.BadRequest)
            val order = call.request.queryParameters["order"] ?: "ASC" //valor por defecto
            val limit = call.request.queryParameters["limit"]?.toIntOrNull() ?: 10 //limite por defecto es 10
            val offset = call.request.queryParameters["offset"]?.toIntOrNull() ?: 0 //el limite por defecto es 0

            val query = FindPostByIdQuery(id = postId, order = order, limit = limit, offset = offset)

            val post = findPostByIdHandler.handle(query)

            if (post.isNotEmpty()) {
                call.respond(post)
            } else {
                call.respondText("Post no encontrado", status = HttpStatusCode.NotFound)
            }

        }
    }
}
