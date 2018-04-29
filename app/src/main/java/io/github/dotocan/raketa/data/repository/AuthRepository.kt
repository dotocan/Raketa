package io.github.dotocan.raketa.data.repository

import io.github.dotocan.raketa.domain.model.websocket.request.*
import io.github.dotocan.raketa.data.service.websocket.WebsocketHandler
import io.github.dotocan.raketa.data.util.hashPassword
import io.github.dotocan.raketa.data.util.randomId
import io.github.dotocan.raketa.domain.repository.IAuthRepository


class AuthRepository: IAuthRepository {

    override fun connect(url: String) {
      WebsocketHandler.websocketService.connectTo(url)

      val connectMsg = ConnectRequest("connect", "1", listOf("1"))
      WebsocketHandler.websocketService.sendConnectMessage(connectMsg)
  }

    override fun login(username: String, password: String) {
        val loginMessage = LoginRequest("method", "login", randomId(), listOf(
                LoginUserParam(LoginUser(username), PasswordParam(hashPassword(password),
                        "sha-256"))))

        WebsocketHandler.websocketService.sendLoginMessage(loginMessage)
    }

    override fun resumeLogin(authToken: String) {
        val resumeLoginRequest = ResumeLoginRequest("method", "login", randomId(), listOf(
                ResumeLoginParam(authToken)
        ))
        WebsocketHandler.websocketService.sendResumeLoginMessage(resumeLoginRequest)
    }
}