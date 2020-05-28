package artifact.networking.interceptor

import artifact.networking.HeaderKeys
import artifact.networking.HeaderValues
import artifact.networking.model.ClientToken
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(var clientToken: ClientToken? = null) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        if (clientToken?.accessToken == null) {
            return chain.proceed(originalRequest)
        }
        val authorizationValue =
            String.format(
                "%s %s",
                HeaderValues.HEADER_VALUE_AUTHORIZATION_BEARER,
                clientToken?.accessToken
            )
        val authorizedRequest = originalRequest.newBuilder()
            .header(HeaderKeys.HEADER_KEY_AUTHORIZATION, authorizationValue).build()
        return chain.proceed(authorizedRequest)
    }
}