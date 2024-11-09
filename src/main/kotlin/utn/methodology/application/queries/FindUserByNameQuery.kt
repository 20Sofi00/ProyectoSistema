package utn.methodology.application.queries

data class FindUserByNameQuery(
    val name: String
) {

    fun validate(): FindUserByNameQuery {
        checkNotNull(name) {throw IllegalArgumentException("El nombre debe ser definido")}
        return this
    }
}
