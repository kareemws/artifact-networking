package artifact.networking.contract.daemon

import artifact.networking.contract.gateway.Gateway
import artifact.networking.model.AuthToken
import artifact.networking.model.ClientToken

abstract class AuthDaemon : Daemon {

    override val gateways: MutableList<Gateway<*>> = mutableListOf()

    var authToken: AuthToken? = null
        set(value) {
            field = value
            notifyAuthTokenUpdate()
        }

    var clientToken: ClientToken? = null
        set(value) {
            field = value
            notifyClientTokenUpdate()
        }

    var tokenUpdateCallback: ((ClientToken) -> Unit)? = null
        set(value) {
            field = value
            notifyTokenCallbackUpdate()
        }

    override fun init() {
        notifyAuthTokenUpdate()
        notifyTokenCallbackUpdate()
        notifyClientTokenUpdate()
    }

    override fun register(gateway: Gateway<*>) {
        gateway.apply {
            authenticator?.tokenUpdateCallback = tokenUpdateCallback
            authenticator?.authToken = authToken
            authInterceptor?.clientToken = clientToken
        }
    }


    private fun notifyAuthTokenUpdate() {
        gateways.forEach {
            it.authenticator?.authToken = authToken
        }
    }

    private fun notifyClientTokenUpdate() {
        gateways.forEach {
            it.authInterceptor?.clientToken = clientToken
        }
    }

    private fun notifyTokenCallbackUpdate() {
        gateways.forEach {
            it.authenticator?.tokenUpdateCallback = tokenUpdateCallback
        }
    }
}