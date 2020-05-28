package artifact.networking.model

open class NetworkConfig(
    val authToken: AuthToken?,
    val gatewaysConfigs: ArrayList<GatewayConfig>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is NetworkConfig) return false

        return authToken == other.authToken && gatewaysConfigs == other.gatewaysConfigs
    }

    override fun hashCode(): Int {
        var result = authToken?.hashCode() ?: 0
        result = 31 * result + gatewaysConfigs.hashCode()
        return result
    }

    override fun toString() =
        "NetworkConfig(authToken=$authToken, gatewaysConfigs=$gatewaysConfigs)"
}