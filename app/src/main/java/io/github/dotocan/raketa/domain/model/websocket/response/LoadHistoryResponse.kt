package io.github.dotocan.raketa.domain.model.websocket.response

/**
 * https://rocket.chat/docs/developer-guides/realtime-api/method-calls/load-history/
 */
data class LoadHistoryResponse(
		val msg: String,
		val id: String,
		val result: LoadHistoryResult
)

data class LoadHistoryResult(
		val messages: List<Message>,
		val unreadNotLoaded: Int
)