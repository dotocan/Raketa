package io.github.dotocan.raketa.domain.model.websocket.request

/**
 * https://rocket.chat/docs/developer-guides/realtime-api/method-calls/login/
 */
data class LoginRequest(
		val msg: String,
		val method: String,
		val id: String,
		val params: List<LoginUserParam>
)

data class LoginUserParam(
        val user: LoginUser,
        val password: PasswordParam
)

data class PasswordParam(
		val digest: String,
		val algorithm: String
)

data class LoginUser(
		val email: String
)