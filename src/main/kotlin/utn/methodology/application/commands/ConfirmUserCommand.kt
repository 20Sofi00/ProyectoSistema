package utn.methodology.application.commands

import utn.methodology.infrastructure.http.router.*
import utn.methodology.application.commandhandlers.*

class ConfirmUserCommand(
    val name: String,
    val userName: String,
    val email: String,
    val password: String,
){
    fun validate(): ConfirmUserCommand{

        checkNotNull(name) { throw IllegalArgumentException("name must be defined") }

        checkNotNull(userName) { throw IllegalArgumentException("userName must be defined") }

        checkNotNull(email) { throw IllegalArgumentException("email must be defined") }

        checkNotNull(password) { throw IllegalArgumentException("Contraseña must be defined") }

        //validar nombre
        if(!validname(name)){
            throw IllegalArgumentException("statusCode 400 / BadRequest: Nombre no valido")
        }
        //validar nombre usuario
        if(!validuserName(userName)){
            throw IllegalArgumentException("statusCode 400 / BadRequest: Nombre de Usuario no valido")
        }

        // Validar el formato del correo electrónico
        if (!validcorreo(email)) {
            throw IllegalArgumentException("statusCode 400 / BadRequest: Correo electrónico no valido")
        }
        //validarcontraseña
        if(!validcontraseña(password)){
            throw  IllegalArgumentException("statusCode 400 / BadRequest: Contraseña no valida")
        }


        return this;
    }
    fun validname(name: String):Boolean{
        val regex =("^[a-zA-Z0-9._-]{3,15}$")
        return regex.toRegex().matches(name)
    }
    fun validuserName(userName: String):Boolean{
        val regex =("^[A-Za-z]{1}([A-Za-z]{2}|[0-9]{2}|[A-Za-z][0-9])([0-9]{4}){1}")
        return regex.toRegex().matches(userName)
    }
    fun validcorreo (email: String):Boolean{
        val regex = ("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
        return regex.toRegex().matches(email)
    }
    fun validcontraseña(password: String):Boolean{
        //mínimo 8 caracteres, letras mayúsculas, minúsculas, y números):
        val regex = ("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")
        return  regex.toRegex().matches(password)
    }
}