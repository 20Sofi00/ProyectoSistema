package utn.methodology.domain.entities

import java.util.UUID

class Usuario (
     val uuid: String,
     val nombre: String,
     val nombreUsuario: String,
     val correoElectronico: String,
     val contrasena: String,
    val followers: MutableList<String> = mutableListOf(),
    val followed: MutableList<String> = mutableListOf()
) {


    companion object {
        fun fromPrimitives(primitives: Map<String, Any>): Usuario {

            val usuario = Usuario(
                primitives["id"] as String,
                primitives["nombre"] as String,
                primitives["nombreUsuario"] as String,
                primitives["correoElectronico"] as String,
                primitives["contrasena"] as String,
                );

            return usuario;


        }

        fun create(

            nombre: String,
            nombreUsuario: String,
            correoElectronico: String,
            contrasena: String,
        ): Usuario {

            val usuario = Usuario(
                UUID.randomUUID().toString(),
                nombre,
                nombreUsuario,
                correoElectronico,
                contrasena,
            )


            return usuario
        }
    }

    fun toPrimitives(): Map<String, Any?> {
        return mapOf(
            "id" to this.uuid,
            "nombre" to this.nombre,
            "nombreUsuario" to this.nombreUsuario,
            "correoElectronico" to this.correoElectronico,
            "contrasena" to this.contrasena
        )
    }

    fun getId(): String {
        return this.uuid;
    }
}