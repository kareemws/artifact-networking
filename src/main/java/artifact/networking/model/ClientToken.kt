package artifact.networking.model

open class ClientToken(
    val accessToken: String?,
    val refreshToken: String?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ClientToken) return false

        return accessToken == other.accessToken && refreshToken == other.refreshToken
    }

    override fun hashCode(): Int {
        var result = accessToken?.hashCode() ?: 0
        result = 31 * result + (refreshToken?.hashCode() ?: 0)
        return result
    }

    override fun toString() = "ClientToken(accessToken=$accessToken, refreshToken=$refreshToken)"
}