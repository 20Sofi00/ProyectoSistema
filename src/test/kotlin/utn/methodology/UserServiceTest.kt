package utn.methodology.infrastructure.persistence.repositories

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.bson.Document
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utn.methodology.domain.entities.models.User
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class UserMongoRepositoryTest {

    private lateinit var database: MongoDatabase
    private lateinit var collection: MongoCollection<Document>
    private lateinit var userRepository: UserMongoRepository

    @BeforeEach
    fun setup() {
        // Mockeamos la base de datos y la colección
        database = mockk()
        collection = mockk()
        every { database.getCollection("users") } returns collection

        // Instanciamos el repositorio con la base de datos mockeada
        userRepository = UserMongoRepository(database)
    }
    @Test
    fun testSaveUser() {
        // Usa el método create para crear el usuario con la contraseña cifrada
        val user = User.create(
            name = "Test User",
            userName = "testuser",
            email = "test@example.com",
            password = "plainPassword"
        )

        // Mock de la operación insertOne
        every { collection.insertOne(any()) } returns mockk()

        // Ejecuta el método save
        userRepository.save(user)

        // Verifica que se llamó a insertOne con el documento correcto
        verify {
            collection.insertOne(
                Document(user.toPrimitives())
            )
        }
    }


    @Test
    fun testFindByIdReturnsUser() {
        val userId = "12345"
        val userDocument = Document("_id", userId)
            .append("name", "Test User")
            .append("userName", "testuser")
            .append("email", "test@example.com")
            .append("password", "hashedPassword")

        every { collection.find(ofType<Document>()).firstOrNull() } returns userDocument

        val user = userRepository.findById(userId)

        assertNotNull(user)
        assertEquals(userId, user?.uuid)
        assertEquals("Test User", user?.name)
        assertEquals("testuser", user?.userName)
        assertEquals("test@example.com", user?.email)
    }

    @Test
    fun testFindByIdReturnsNullWhenUserNotFound() {
        // Mock del método `find` en la colección, especificando el tipo con `ofType<Document>()`
        every { collection.find(ofType<Document>()).firstOrNull() } returns null

        val user = userRepository.findById("nonexistent-id")

        assertNull(user) // Verifica que el resultado sea `null` cuando no se encuentra el usuario
    }
}