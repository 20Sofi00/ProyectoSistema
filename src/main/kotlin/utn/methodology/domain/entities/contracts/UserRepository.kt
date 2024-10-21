package utn.methodology.domain.entities.contracts
import utn.methodology.domain.entities.models.User

interface UserRepository {
        fun save(shipping: User)
        fun findOne(id: String): User?
    abstract fun findById(followerId: String): User
    

    }

