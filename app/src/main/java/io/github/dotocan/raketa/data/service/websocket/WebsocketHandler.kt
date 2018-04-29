package io.github.dotocan.raketa.data.service.websocket

/**
 * Keeps a static reference to WebsocketService so the same instance can be shared
 * between multiple activities
 */
class WebsocketHandler {
    companion object {
        lateinit var websocketService: WebsocketService


    }
}