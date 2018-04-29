package io.github.dotocan.raketa.domain.model.websocket.response

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * https://rocket.chat/docs/developer-guides/realtime-api/method-calls/get-rooms/
 */
data class GetRoomsResponse(
		val msg: String,
		val id: String,
		val result: GetRoomsResponseResult
)

data class GetRoomsResponseResult(
        val update: List<Room>
      //  val remove: List<String>
)

@Entity(tableName = "ROOMS")
data class Room(
		@SerializedName("_id") @PrimaryKey val roomId: String,
		val t: String?,
		val name: String?,
		@Embedded val u: U?,
		val topic: String?,
		//val muted: String, // TODO List<String>
		@Embedded val jitsiTimeout: JitsiTimeout?,
		val ro: Boolean?
)

data class U(
		@SerializedName("_id") val userId: String?,
		val username: String?
)

data class JitsiTimeout(
        @SerializedName("\$date") val date: Long?
)