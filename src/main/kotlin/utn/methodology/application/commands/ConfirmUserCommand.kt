package utn.methodology.application.commands

import utn.methodology.infrastructure.http.router.*
import utn.methodology.application.commandhandlers.*

class ConfirmUserCommand(
    val nombre: String,
    val nombreUsuario: String,
    val correoElectronico: String,
    val contrasena: String,
){
    fun validate(): ConfirmUserCommand{

        checkNotNull(nombre) { throw IllegalArgumentException("Nombre must be defined") }

        checkNotNull(nombreUsuario) { throw IllegalArgumentException("NombreUsuario must be defined") }

        checkNotNull(correoElectronico) { throw IllegalArgumentException("CorreoElectronico must be defined") }

        checkNotNull(contrasena) { throw IllegalArgumentException("Contraseña must be defined") }

        //validar nombre
        if(!validnombre(nombre)){
            throw IllegalArgumentException("statusCode 400 / BadRequest: Nombre no valido")
        }
        //validarnombreusuario
        if(!validnombreusuario(nombreUsuario)){
            throw IllegalArgumentException("statusCode 400 / BadRequest: Nombre de Usuario no valido")
        }

        // Validar el formato del correo electrónico
        if (!validcorreo(correoElectronico)) {
            throw IllegalArgumentException("statusCode 400 / BadRequest: Correo electrónico no valido")
        }
        //validarcontraseña
        if(!validcontraseña(contrasena)){
            throw  IllegalArgumentException("statusCode 400 / BadRequest: Contraseña no valida")
        }


        return this;
    }
    fun validnombre(nombre: String):Boolean{
        val regex =("^[a-zA-Z0-9._-]{3,15}$")
        return regex.toRegex().matches(nombre)
    }
    fun validnombreusuario(nombreUsuario: String):Boolean{
        val regex =("^[A-Za-z]{1}([A-Za-z]{2}|[0-9]{2}|[A-Za-z][0-9])([0-9]{4}){1}")
        return regex.toRegex().matches(nombreUsuario)
    }
    fun validcorreo (correoElectronico: String):Boolean{
        val regex = ("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
        return regex.toRegex().matches(correoElectronico)
    }
    fun validcontraseña(contrasena: String):Boolean{
        //mínimo 8 caracteres, letras mayúsculas, minúsculas, y números):
        val regex = ("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")
        return  regex.toRegex().matches(contrasena)
    }
}