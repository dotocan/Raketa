package io.github.dotocan.raketa.domain.model.websocket.request


data class SendMessageRequest(
		val msg: String,
		val method: String,
		val id: String,
		val params: List<SendMessageParam>
)

data class SendMessageParam(
		val _id: String,
		val rid: String,
		val msg: String
)