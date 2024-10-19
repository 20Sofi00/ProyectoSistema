package utn.methodology.domain.entities.contracts
import utn.methodology.domain.entities.Usuario

interface UsuarioRepository {
        fun save(shipping: Usuario)
        fun findOne(id: String): Usuario?
    abstract fun findById(followerId: String): Usuario

}

