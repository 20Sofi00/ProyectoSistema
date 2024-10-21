package utn.methodology.application.commands

data class FollowUserCommand (
    val userId: String,
    val followedId: String
)