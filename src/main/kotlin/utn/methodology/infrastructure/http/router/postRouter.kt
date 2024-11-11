package utn.methodology.infrastructure.http.router

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import utn.methodology.application.commands.CreatePostCommand
import utn.methodology.application.commandhandlers.CreatePostHandler
import utn.methodology.application.services.PostService
import utn.methodology.infrastructure.http.actions.CreatePostAction
import utn.methodology.infrastructure.persistence.connectToMongoDB
import utn.methodology.infrastructure.persistence.repositories.MongoPostRepository
import utn.methodology.application.commandhandlers.DeletePostHandler
import utn.methodology.application.commands.DeletePostCommand
import utn.methodology.application.queries.FindPostByIdQuery
import utn.methodology.application.queryhandlers.FindPostByIdHandler
import utn.methodology.infrastructure.http.actions.DeletePostAction
import utn.methodology.infrastructure.http.actions.FindPostByIdAction
import utn.methodology.infrastructure.persistence.repositories.UserMongoRepository

//fun main() {
////    embeddedServer(Netty, port = 8080, module = Application::module).start(wait = true)
////}

fun Application.postRouter() {
    // Conectar a la base de datos MongoDB
    val mongoDatabase = connectToMongoDB()

    // Crear el repositorio de posts
    val postRepository = MongoPostRepository(mongoDatabase)

    // Crear el repositorio de usuarios
    val userMongoRepository = UserMongoRepository(mongoDatabase)

    // Crear el servicio de posts
    val postService = PostService(userMongoRepository, postRepository)

    // Crear el handler y la acción para crear posts
    val createPostHandler = CreatePostHandler(postRepository)
    val createPostAction = CreatePostAction(createPostHandler)
    // Crear el handler y la acción para eliminar posts
    val deletePostHandler = DeletePostHandler(postRepository)
    val deletePostAction = DeletePostAction(deletePostHandler)
    val findPostByIdAction = FindPostByIdAction(FindPostByIdHandler(postRepository, userMongoRepository))

    routing {
        post("/posts") {
            val body = call.receive<CreatePostCommand>()
            createPostAction.execute(body)
            call.respond(HttpStatusCode.Created, mapOf("message" to "Post creado exitosamente"))
        }

//        // Definir las rutas para los posts
//        postRoutes(postService)
        delete("/post/{id}") {

            // Obtener el ID del post de los parámetros de la ruta
            val postId = call.parameters["id"].toString()

            // Obtener el ID del usuario, asumido que es enviado en los parámetros de la consulta o el cuerpo de la petición
            val userId = call.request.queryParameters["userId"] ?: return@delete call.respond(
                HttpStatusCode.BadRequest,
                "Missing userId"
            )


            // Responder con el código de éxito apropiado (204 No Content)
            call.respond(HttpStatusCode.NoContent)
        }
    }



    // Definir las rutas para los posts
    fun Route.postRoutes(postService: PostService) {
        get("/posts/user/{userId}") {
            val userId =
                call.parameters["userId"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing userId")
            val posts = postService.getPostsByFollowedUsers(userId)

            if (posts.isNotEmpty()) {
                call.respond(HttpStatusCode.OK, posts)
            } else {
                call.respond(HttpStatusCode.NotFound, "No se encontraron los post para los usuarios seguidos del usuario: $userId")
            }
        }
        get("/posts") {
            // Extraer parámetros de búsqueda desde la query. Se definen los valores por defecto exepto el userId
            val userId = call.request.queryParameters["user_id"]
            val order = call.request.queryParameters["order"] ?: "DESC"
            val limit = call.request.queryParameters["limit"]?.toIntOrNull() ?: 10
            val offset = call.request.queryParameters["offset"]?.toIntOrNull() ?: 0

            // Validar que el userId no sea nulo
            if (userId == null) {
                call.respond(HttpStatusCode.BadRequest, "Falta el parámetro 'user_id'")
                return@get
            }

            try {
                // Validar parámetros adicionales
                if (limit !in 1..500) throw IllegalArgumentException("El límite debe estar entre 1 y 500")
                if (offset < 0) throw IllegalArgumentException("El offset debe ser mayor o igual a 0")
                if (order.uppercase() !in listOf(
                        "ASC",
                        "DESC"
                    )
                ) throw IllegalArgumentException("El orden debe ser 'ASC' o 'DESC'")

                // Obtener posts desde el servicio
                val posts = findPostByIdAction.execute(FindPostByIdQuery(userId.toString(), order.uppercase(), limit, offset))

                // Responder con los posts obtenidos
                call.respond(HttpStatusCode.OK, Json.encodeToString(posts))
            } catch (e: IllegalArgumentException) {
                println("error de obtener los posts")
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
            } catch (e: Exception) {
                println("exeption  de obtener los posts")
                call.respond(HttpStatusCode.InternalServerError, "Ocurrió un error inesperado")
            }
        }


        get("/posts/{id}") {
            val postId = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Falta el Id del post")
            val post = postService.getPostById(postId)

            if (post != null) {
                call.respond(HttpStatusCode.OK, post)
            } else {
                call.respond(HttpStatusCode.NotFound, "Post no encontrado con el ID: $postId")
            }
        }
    }
}