package io.github.dotocan.raketa.domain.model.websocket.response

import com.google.gson.annotations.SerializedName

/**
 * https://rocket.chat/docs/developer-guides/realtime-api/method-calls/login/
 */
data class ResumeLoginResponse(
        val msg: String,
        val id: String,
        val result: ResumeLoginResult
)

data class ResumeLoginResult(
        @SerializedName("id") val userId: String,
        val token: String,
        val tokenExpires: TokenExpires,
        val type: String
)