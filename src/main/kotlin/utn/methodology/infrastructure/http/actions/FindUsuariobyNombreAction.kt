package utn.methodology.infrastructure.http.actions

import utn.methodology.application.queries.FindUsuariobyNombreQuery
import utn.methodology.application.queryhandlers.FindUserByNombreHandler

class FindUsuariobyNombreAction(
    private val handler: FindUserByNombreHandler
) {

    fun execute(query: FindUsuariobyNombreQuery): Map<String, String> {
        query
            .validate()
            .let {
                return handler.handle(it)
            }

    }


}
