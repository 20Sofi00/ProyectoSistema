package utn.methodology.application.queries

data class FindUsuariobyNameQuery(
    val name: String
) {

    fun validate(): FindUsuariobyNameQuery {
        checkNotNull(name) {throw IllegalArgumentException("El nombre debe ser definido")}
        return this
    }
}