package utn.methodology.application.queries

class FindPostByIdQuery (
    val id: String,
    val order: String,
    val limit: Int,
    val offset: Int
    ) {
    fun Validate(): FindPostByIdQuery{
        checkNotNull(id) { throw IllegalArgumentException("Id debe estar definido") }
        return this
    }


}
