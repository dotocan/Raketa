package io.github.dotocan.raketa.domain.model.websocket.request

data class StreamRoomMessagesRequest(
		val msg: String,
		val id: String,
		val name: String,
		val params: List<Any>
)