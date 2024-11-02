package utn.methodology


import example.com.application.commandhandlers.CreatePostHandler
import example.com.application.commands.CreatePostCommand
import example.com.shared.mocks.MockPostRepository
import example.com.shared.mocks.MockUserRepository
import example.com.shared.mothers.PostMother
import example.com.shared.mothers.UserMother
import kotlin.test.BeforeTest
import kotlin.test.Test

class CreatePostHandlerTest {

    private val mockUserRepository: MockUserRepository = MockUserRepository()
    private val mockPostRepository: MockPostRepository = MockPostRepository()

    private var sut: CreatePostHandler = CreatePostHandler(mockUserRepository, mockPostRepository)

    @BeforeTest
    fun beforeEach() {
        mockPostRepository.clean()
        mockUserRepository.clean()
    }

    @Test
    fun `should create a post and persist into database`() {
        // arrange
        val user = UserMother.random()
        val content = PostMother.faker.southPark.quotes()

        mockUserRepository.save(user)

        val command = CreatePostCommand(
            content,
            user.getId()
        )

        // act
        sut.handle(command)

        // assertions
        val posts = mockPostRepository.findByOwnerId(user.getId())

        assert(posts.size == 1)
        assert(posts[0].getContent() === content)
    }
}