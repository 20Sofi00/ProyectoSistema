package utn.methodology.application.queries

data class FindUsuariobyNombreQuery(
    val nombre: String
) {

    fun validate(): FindUsuariobyNombreQuery {
        checkNotNull(nombre) {throw IllegalArgumentException("El nombre debe ser definido")}
        return this
    }
}