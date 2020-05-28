package artifact.networking.contract.gateway

import artifact.networking.contract.Authenticator
import artifact.networking.contract.config.HttpConfig
import artifact.networking.interceptor.AuthInterceptor
import artifact.networking.model.GatewayConfig
import artifact.networking.contract.config.RetrofitConfig
import okhttp3.Interceptor

abstract class Gateway<ServiceInterface> {
    abstract val gatewayConfig: GatewayConfig

    protected abstract val servicesContainer: Class<ServiceInterface>

    abstract val retrofitConfig: RetrofitConfig

    abstract val httpConfig: HttpConfig

    open val interceptors: MutableList<Interceptor> = mutableListOf()

    open val networkInterceptors: MutableList<Interceptor> = mutableListOf()

    open val authenticator: Authenticator? = null

    open val authInterceptor: AuthInterceptor? = null

    val invocationObject: ServiceInterface
        get() {
            val baseUrl = "${gatewayConfig.baseUrl}${gatewayConfig.pathPrefix ?: ""}"
            val httpClient = httpConfig.forgeHttpClient(
                authenticator,
                authInterceptor,
                interceptors,
                networkInterceptors
            )
            val retrofitClient = retrofitConfig.forgeRetrofitClient(baseUrl, httpClient)
            return retrofitClient.create(servicesContainer)
        }
}