package artifact.networking.contract.config

import artifact.networking.contract.Authenticator
import artifact.networking.interceptor.AuthInterceptor
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

abstract class HttpConfig {
    protected abstract val connectionTimeout: Long

    protected abstract val readTimeout: Long

    protected abstract val writeTimeout: Long

    protected abstract val cache: Cache?

    protected open val timeUnit = TimeUnit.SECONDS

    protected open val interceptors = emptyList<Interceptor>()

    protected open val networkInterceptors = emptyList<Interceptor>()

    fun forgeHttpClient(
        authenticator: Authenticator? = null,
        authInterceptor: AuthInterceptor? = null,
        externalInterceptors: List<Interceptor> = emptyList(),
        externalNetworkInterceptors: List<Interceptor> = emptyList()
    ) = OkHttpClient().newBuilder()
        .connectTimeout(connectionTimeout, timeUnit)
        .readTimeout(readTimeout, timeUnit)
        .writeTimeout(writeTimeout, timeUnit)
        .cache(cache)
        .apply {
            interceptors()
                .addAll(interceptors + externalInterceptors)
            networkInterceptors()
                .addAll(networkInterceptors + externalNetworkInterceptors)
            authenticator?.let {
                authenticator(it)
            }
            authInterceptor?.let {
                networkInterceptors().add(it)
            }
        }.build()
}