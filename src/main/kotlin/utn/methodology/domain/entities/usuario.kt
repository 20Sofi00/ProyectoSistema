package utn.methodology.domain.entities

import java.util.UUID

class usuario (
    private val uuid: String,
    private val nombre: String,
    private val nombreUsuario: String,
    private val correoElectronico: String,
    private val contrasena: String,
) {


    companion object {
        fun fromPrimitives(primitives: Map<String, Any>): usuario {

            val Usuario = usuario(
                primitives["id"] as String,
                primitives["nombre"] as String,
                primitives["nombreUsuario"] as String,
                primitives["correoElectronico"] as String,
                primitives["contrasena"] as String,
                );

            return Usuario;


        }

        fun create(
            id: String,
            nombre: String,
            nombreUsuario: String,
            correoElectronico: String,
            contrasena: String,
        ): usuario {

            val usuario = usuario(
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


}