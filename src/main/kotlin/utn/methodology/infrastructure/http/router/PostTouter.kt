package utn.methodology.infrastructure.http.router

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import utn.methodology.application.commandhandlers.ConfirmUserHandler
import utn.methodology.infrastructure.http.actions.ConfirmUsuarioAction
import utn.methodology.infrastructure.persistence.connectToMongoDB
import utn.methodology.infrastructure.persistence.repositories.UsuarioMongoRepository

fun Application.Postrouter(){
    val mongoDatabase = connectToMongoDB()
    val usuarioRepository = UsuarioMongoRepository(mongoDatabase)
    val confirmUsuarioAction =
        ConfirmUsuarioAction(ConfirmUserHandler(usuarioRepository))


routing{
    get("/posts"){
        val users = usuarioRepository.findOne("3")
        val order =call.request.queryParameters["order"] ?:"ASC" //valor por defecto
        val limit = call.request.queryParameters["limit"]?.toIntOrNull()?:10 //limite por defecto es 10
        val offset = call.request.queryParameters["offset"] ?.toIntOrNull()?:0 //el limite por defecto es 0
        //validar los parametros
        //deberia validar el usuario?
        if(limit<1 || limit > 100){
            call.respond(HttpStatusCode.BadRequest, "El limite debe ser entre 1 y 100")
            return@get
        }
        if(order != "ASC"){
            call.respond(HttpStatusCode.BadRequest, "El orden debe ser ascendente ")
            return@get
        }
        if (offset < 0) {
            call.respond(HttpStatusCode.BadRequest, "El la paginacion debe ser mayor a cero")
            return@get
        }


    }
}

}


