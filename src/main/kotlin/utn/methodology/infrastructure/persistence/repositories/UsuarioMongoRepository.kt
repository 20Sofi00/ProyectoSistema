package utn.methodology.infrastructure.persistence.repositories


import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.UpdateOptions
import utn.methodology.domain.entities.Usuario
//import io.github.cdimascio.dotenv.dotenv
import org.bson.Document

class MongoUserRepository(private val database: MongoDatabase) {

    private var collection: MongoCollection<Any>;

    init {
        collection = database.getCollection("users") as MongoCollection<Any>;
    }



    fun save(usuario: Usuario) {
        println("UsuarioMongoRepository - Saving usuario: $usuario")
        val options = UpdateOptions().upsert(true);

        val filter = Document("_id", usuario.getId()) // Usa el campo id como filter
        val update = Document("\$set", usuario.toPrimitives())

        collection.updateOne(filter, update, options)
    }


    fun findOne(id: String): Usuario? {
        val filter = Document("_id", id);

        val primitives = collection.find(filter).firstOrNull();

        if (primitives == null) {
            return null;
        }

        return Usuario.fromPrimitives(primitives as Map<String, String>)
    }
}
