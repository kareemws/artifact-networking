package artifact.networking.contract

import artifact.networking.HeaderKeys
import artifact.networking.HeaderValues
import artifact.networking.model.AuthToken
import artifact.networking.model.ClientToken
import kotlinx.coroutines.runBlocking
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

abstract class Authenticator(
    var tokenUpdateCallback: ((ClientToken) -> Unit)?,
    var authToken: AuthToken? = null
) : okhttp3.Authenticator {

    abstract suspend fun generateNewClientToken(authToken: AuthToken): ClientToken?

    override fun authenticate(route: Route?, response: Response): Request? =
        authToken?.let { authToken ->
            val newClientToken = runBlocking {
                generateNewClientToken(authToken)
            }
            newClientToken?.let { clientToken ->
                tokenUpdateCallback?.invoke(clientToken)
                val authorizationHeaderValue = String.format(
                    "%s %s",
                    HeaderValues.HEADER_VALUE_AUTHORIZATION_BEARER,
                    clientToken.accessToken
                )
                response.request.newBuilder().header(
                    HeaderKeys.HEADER_KEY_AUTHORIZATION, authorizationHeaderValue
                ).build()
            }
        }
}