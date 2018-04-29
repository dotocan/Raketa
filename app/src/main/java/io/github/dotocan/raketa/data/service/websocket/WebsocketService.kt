package io.github.dotocan.raketa.data.service.websocket

import com.google.gson.Gson
import io.github.dotocan.raketa.domain.model.websocket.*
import io.github.dotocan.raketa.domain.model.websocket.request.*
import io.github.dotocan.raketa.domain.model.websocket.response.*
import io.github.dotocan.raketa.util.extensions.debug
import okhttp3.*
import okio.ByteString
import java.util.concurrent.TimeUnit

/**
 * Created by dotocan on 12.3.2018..
 */

class WebsocketService {

    private var ws: WebSocket? = null

    val messageParser = MessageParser()
    private val gson = Gson()

    val ping: String = gson.toJson(PingPong("ping"))
    val pong: String = gson.toJson(PingPong("pong"))

    // Building OkHttp client
    private val client = OkHttpClient.Builder()
            .readTimeout(0, TimeUnit.MILLISECONDS)
            .build()

    fun connectTo(serverUrl: String) {
        val fullUrl = "wss://$serverUrl/websocket"

        debug("Connecting to $fullUrl")

        // Building OkHttp request
        val request = Request.Builder()
                .url(fullUrl)
                .build()

        ws = client.newWebSocket(request, listener)
    }

    fun sendRequestMessage(reqestMsg: String) {
        ws?.send(reqestMsg)
    }

    fun sendConnectMessage(connectRequest: ConnectRequest) {
        val connectRequestString = gson.toJson(connectRequest)
        ws?.send(connectRequestString)
    }

    fun sendLoginMessage(loginRequest: LoginRequest) {
        messageParser.requestResponseMap[loginRequest.id] = LoginResponse::class.java
        val loginRequestString = gson.toJson(loginRequest)
        ws?.send(loginRequestString)
    }

    fun sendResumeLoginMessage(resumeLoginRequest: ResumeLoginRequest) {
        messageParser.requestResponseMap[resumeLoginRequest.id] = ResumeLoginResponse::class.java
        val resumeLoginRequestString = gson.toJson(resumeLoginRequest)
        ws?.send(resumeLoginRequestString)
    }

    fun sendGetRoomsMessage(getRoomsRequest: GetRoomsRequest) {
        messageParser.requestResponseMap[getRoomsRequest.id] = GetRoomsResponse::class.java
        val getRoomsRequestString = gson.toJson(getRoomsRequest)
        ws?.send(getRoomsRequestString)
    }

    fun sendLoadHistoryMessage(loadHistoryRequest: LoadHistoryRequest) {
        messageParser.requestResponseMap[loadHistoryRequest.id] = LoadHistoryResponse::class.java
        val loadHistoryRequestString = gson.toJson(loadHistoryRequest)
        ws?.send(loadHistoryRequestString)
    }

    fun sendStreamRoomMessagesMessage(streamRoomMessagesRequest: StreamRoomMessagesRequest) {
        messageParser.requestResponseMap[streamRoomMessagesRequest.id] = StreamRoomMessagesResponse::class.java
        val streamRoomMessagesRequestString = gson.toJson(streamRoomMessagesRequest)
        ws?.send(streamRoomMessagesRequestString)
    }

    fun sendSendMessageRequest(sendMessageRequest: SendMessageRequest) {
       // No need to parse response since we are subscribed for receiving messages
        val sendMessageRequestString = gson.toJson(sendMessageRequest)
        ws?.send(sendMessageRequestString)
    }

    // Creating a listener for WebSocket communication
    private val listener = object : WebSocketListener() {

        override fun onOpen(webSocket: WebSocket, response: Response) {
            debug("onOpen " + response.message())
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            debug("onMessage $text")

            // Keep the connection open with ping-pong messages
            if (text == ping) {
                ws?.send(pong)
            }
            else {
                messageParser.parseMessage(text)
            }
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            debug("onMessage bytes " + bytes.toString())
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            debug("onClosing reason $reason")
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            debug("onFailure response " + t.message)
            super.onFailure(webSocket, t, response)
        }
    }
}