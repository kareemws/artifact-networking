package artifact.networking.model

open class AuthToken(
    val clientId: String,
    val clientSecret: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AuthToken) return false

        return clientId == other.clientId && clientSecret == other.clientSecret
    }

    override fun hashCode(): Int {
        var result = clientId.hashCode()
        result = 31 * result + clientSecret.hashCode()
        return result
    }

    override fun toString() = "AuthToken(clientId='$clientId', clientSecret='$clientSecret')"

}