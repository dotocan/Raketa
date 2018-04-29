package io.github.dotocan.raketa.domain.model.websocket.request

/**
 * https://rocket.chat/docs/developer-guides/realtime-api/method-calls/login/
 */
data class ResumeLoginRequest(
		val msg: String,
		val method: String,
		val id: String,
		val params: List<ResumeLoginParam>
)

data class ResumeLoginParam(
		val resume: String
)