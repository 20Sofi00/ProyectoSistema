package utn.methodology.infrastructure.http.actions
import utn.methodology.application.queries.FindUserByNameQuery
import utn.methodology.application.queryhandlers.FindUserByNameHandler
class FindUserByNameAction(
    private val handler: FindUserByNameHandler
) {
    fun execute(query: FindUserByNameQuery): Map<String, String> {
        query
            .validate()
            .let {
                return handler.handle(it)
            }
    }
}
