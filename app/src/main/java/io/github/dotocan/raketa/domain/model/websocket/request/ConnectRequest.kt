package io.github.dotocan.raketa.domain.model.websocket.request

data class ConnectRequest(
        val msg: String,
        val version: String,
        val support: List<String>
)