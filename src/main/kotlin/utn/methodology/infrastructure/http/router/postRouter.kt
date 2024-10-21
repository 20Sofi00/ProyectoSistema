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
import utn.methodology.infrastructure.http.actions.CreatePostAction
import utn.methodology.infrastructure.persistence.connectToMongoDB
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

    routing {
        post("/posts") {
            val body = call.receive<CreatePostCommand>()
            createPostAction.execute(body)
            call.respond(HttpStatusCode.Created, mapOf("message" to "Post creado exitosamente"))
        }
    }
    fun Route.postRoutes() {

        get("/posts/user/{userId}") {
            val userId = call.parameters["userId"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing userId")
            val posts = PostService.getPostsByFollowedUsers(userId)

            if (posts.isNotEmpty()) {
                call.respond(HttpStatusCode.OK, posts)
            } else {
                call.respond(HttpStatusCode.NotFound, "No posts found for followed users of userId: $userId")
            }
        }


        get("/posts/{id}") {
            val postId = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing post ID")

            val post = PostService.getPostById(postId)

            if (post != null) {
                call.respond(HttpStatusCode.OK, post)
            } else {
                call.respond(HttpStatusCode.NotFound, "Post not found with ID: $postId")
            }
        }
    }
}
