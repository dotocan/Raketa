package io.github.dotocan.raketa.domain.model.websocket.response

import com.google.gson.annotations.SerializedName


data class StreamRoomMessagesResponse(
		val msg: String,
		val collection: String,
		val id: String,
		val fields: Fields
)

data class Fields(
		val eventName: String,
		val args: List<Arg>
)

data class Arg(
		val _id: String,
		val rid: String,
		val msg: String,
		val ts: StreamRoomMessagesTs,
		val u: StreamRoomMessagesU,
		val mentions: List<Any>,
		val channels: List<Any>,
		val _updatedAt: StreamRoomMessagesUpdatedAt
)

data class StreamRoomMessagesTs(
		@SerializedName("\$date") val date: Long
)

data class StreamRoomMessagesUpdatedAt(
        @SerializedName("\$date") val date: Long
)

data class StreamRoomMessagesU(
		val _id: String,
		val username: String,
		val name: String
)