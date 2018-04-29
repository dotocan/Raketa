package io.github.dotocan.raketa.domain.model.websocket

abstract class SubscriptionMessage(
		val id: String,
		val msg: String,
		val name: String,
		val params: List<String>
)

abstract class MethodMessage(
		val id: String,
		val msg: String,
		val method: String,
		val params: List<String>
)

abstract class ResultMessage(
		val id: String,
		val msg: String,
        val result: String // JSON object
)

data class UpdatedAt(
		val date: Long
)

