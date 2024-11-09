package utn.methodology.infrastructure.http

import io.ktor.server.application.*
import io.ktor.server.routing.*
import utn.methodology.infrastructure.persistence.connectToMongoDB
import utn.methodology.infrastructure.persistence.repositories.UserMongoRepository
import utn.methodology.infrastructure.persistence.repositories.MongoPostRepository
import utn.methodology.application.services.PostService
import utn.methodology.infrastructure.http.router.postRoutes


fun Application.module() {
    val database = connectToMongoDB()
    val userRepository = UserMongoRepository(database)
    val postRepository = MongoPostRepository(database)
    val postService = PostService(userRepository, postRepository)

    routing {
        postRoutes(postService)
    }
}