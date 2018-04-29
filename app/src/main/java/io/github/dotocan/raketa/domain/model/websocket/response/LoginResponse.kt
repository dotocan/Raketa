package io.github.dotocan.raketa.domain.model.websocket.response

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * https://rocket.chat/docs/developer-guides/realtime-api/method-calls/login/
 */
@Entity(tableName = "LOGIN_RESPONSES")
data class LoginResponse(
		@PrimaryKey(autoGenerate = true) val dbId: Long,
		val msg: String,
		val id: String,
		@Embedded val result: LoginResult
)

data class LoginResult(
		@SerializedName("id") val userId: String,
		val token: String,
		@Embedded val tokenExpires: TokenExpires,
		val type: String
)

data class TokenExpires(
		@SerializedName("\$date") val date: Long
)