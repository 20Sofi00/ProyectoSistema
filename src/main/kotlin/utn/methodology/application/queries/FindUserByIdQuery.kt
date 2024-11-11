package utn.methodology.application.queries

data class FindUserByIdQuery(
    val id: String
) {

    fun validate(): FindUserByIdQuery {
        checkNotNull(id) {throw IllegalArgumentException("ERROR: Ingrese un id correcto")}
        return this
    }
}
