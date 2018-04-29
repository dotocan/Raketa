package io.github.dotocan.raketa.domain.model.websocket.request

import com.google.gson.annotations.SerializedName

/**
 * https://rocket.chat/docs/developer-guides/realtime-api/method-calls/get-rooms/
 */
data class GetRoomsRequest(
		val msg: String,
		val method: String,
		val id: String,
		val params: List<GetRoomsParam>
)

data class GetRoomsParam(
		@SerializedName("\$date") val date: Long
)