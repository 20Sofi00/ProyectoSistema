package utn.methodology.application.queries

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.utils.io.core.*
import java.time.LocalDateTime

class FindPostByIdQuery(

    val id:String,
    val order:String ="ASC",
    val limit: Int =10,
    val offset: Int =0)

{
    fun Validate(){
     require(limit in 1..100){"El limite debe ser entre 1 y 100"}
     require(order == "ASC") {"El orden debe ser ascendente"}
     require(offset >= 0) {"En la paginacion debe ser mayor a cero"}

 }
}