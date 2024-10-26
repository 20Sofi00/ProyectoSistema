package utn.methodology.domain.entities.contracts
import utn.methodology.domain.entities.User
import utn.methodology.domain.entities.models.User

interface UserRepository {
        fun save(shipping: User)
        fun findOne(id: String): User?
     fun findById(followerId: String): User?
    

    }

