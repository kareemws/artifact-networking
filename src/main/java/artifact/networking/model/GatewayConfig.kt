package artifact.networking.model

open class GatewayConfig(
    val baseUrl: String,
    val pathPrefix: String?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is GatewayConfig) return false

        return baseUrl == other.baseUrl && pathPrefix == other.pathPrefix
    }

    override fun hashCode(): Int {
        var result = baseUrl.hashCode()
        result = 31 * result + (pathPrefix?.hashCode() ?: 0)
        return result
    }

    override fun toString() = "GatewayConfig(baseUrl='$baseUrl', pathPrefix=$pathPrefix)"
}