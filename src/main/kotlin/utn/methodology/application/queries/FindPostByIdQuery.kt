package utn.methodology.application.queries

class FindPostByIdQuery (
    val id: String,
    val order: String,
    val limit: Int,
    val offset: Int
    ) {
    fun Validate(): FindPostByIdQuery{
        require(limit in 1..500){"El limite debe ser entre 1 y 500"}
        require(order == "ASC") {"El orden debe ser ascendente"}
        require(offset >= 0) {"En la paginacion debe ser mayor a cero"}
        checkNotNull(id) { throw IllegalArgumentException("Id debe estar definido") }
        return this
    }
}

