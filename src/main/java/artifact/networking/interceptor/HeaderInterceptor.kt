package artifact.networking.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(
    private val headerKey: String,
    private val headerValue: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val modifiedRequest = originalRequest.newBuilder()
            .header(headerKey, headerValue).build()
        return chain.proceed(modifiedRequest)
    }
}