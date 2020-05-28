package artifact.networking.contract.daemon

import artifact.networking.contract.gateway.Gateway

interface Daemon {

    val gateways: MutableList<Gateway<*>>

    fun init()

    fun register(gateway: Gateway<*>)
}