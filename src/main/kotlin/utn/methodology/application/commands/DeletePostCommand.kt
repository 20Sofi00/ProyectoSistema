package utn.methodology.application.commands

data class DeletePostCommand(
    val postId: String,
    val userId: String
) {
    fun validate(): DeletePostCommand{
        checkNotNull(postId) {throw IllegalArgumentException("Id must be defined")}
        return this
    }
}
