package io.github.dotocan.raketa.data.service.websocket

import android.arch.lifecycle.MutableLiveData
import com.google.gson.Gson
import io.github.dotocan.raketa.db
import io.github.dotocan.raketa.domain.model.websocket.response.*
import io.github.dotocan.raketa.util.extensions.debug
import org.jetbrains.anko.doAsync
import org.json.JSONObject

/**
 * Created by dotocan on 12.3.2018..
 */

class MessageParser {
    private val gson = Gson()

    // Holds request message id and type to convert response message to
    val requestResponseMap = mutableMapOf<String, Any>()

    companion object {
        val connectResponseLiveData = MutableLiveData<ConnectResponse>()
        val resumeLoginResponseLiveData = MutableLiveData<ResumeLoginResponse>()
    }

    fun parseMessage(msg: String) {

        doAsync {

            try {
                val json = JSONObject(msg)
                val responseType = json["msg"] as String

                if (responseType == "connected") {
                    parseConnectResponseMessage(msg)
                    return@doAsync
                }

                // Response messages have the same id as request messages. This way we can know
                // which response message corresponds to which request message, and how to convert them
                val responseId = json["id"] as String
                for ((requestId, targetType) in requestResponseMap) {
                    if (responseId == requestId) {
                        debug("responseId: $responseId, id: $requestId, targetType: $targetType")
                        parseResponseMessageToType(msg, targetType)
                    }
                }
                if(responseId == "id") {
                    parseStreamRoomMessagesResponseMessage(msg)
                }

            } catch (t: Throwable) {
                debug("MessageParser error: $t")
            }
        }
    }

    private fun parseResponseMessageToType(msg: String, type: Any) {
        when (type) {
            LoginResponse::class.java -> parseLoginResponseMessage(msg)
            ResumeLoginResponse::class.java -> parseResumeLoginResponseMessage(msg)
            GetRoomsResponse::class.java -> parseGetRoomsResponseMessage(msg)
            LoadHistoryResponse::class.java -> parseLoadHistoryResponseMessage(msg)
            StreamRoomMessagesResponse::class.java -> parseStreamRoomMessagesResponseMessage(msg)
        }
    }

    private fun parseConnectResponseMessage(msg: String) {
        val connectResponse = gson.fromJson(msg, ConnectResponse::class.java)
        connectResponseLiveData.postValue(connectResponse)
    }

    private fun parseLoginResponseMessage(msg: String) {
        val loginResponse = gson.fromJson(msg, LoginResponse::class.java)
        db.loginResponseDao().insert(loginResponse)
    }

    private fun parseResumeLoginResponseMessage(msg: String) {
        val resumeLoginResponse = gson.fromJson(msg, ResumeLoginResponse::class.java)
        resumeLoginResponseLiveData.postValue(resumeLoginResponse)
    }

    private fun parseGetRoomsResponseMessage(msg: String) {
        val roomsResponse = gson.fromJson(msg, GetRoomsResponse::class.java)
        db.roomsDao().insertAll(roomsResponse.result.update)
    }

    private fun parseLoadHistoryResponseMessage(msg: String) {
        val loadHistoryResponse = gson.fromJson(msg, LoadHistoryResponse::class.java)
        db.messagesDao().insertAll(loadHistoryResponse.result.messages)
    }

    private fun parseStreamRoomMessagesResponseMessage(msg: String) {
        debug("STREAM MESSAGE: $msg")
        val streamRoomMessagesResponse = gson.fromJson(msg, StreamRoomMessagesResponse::class.java)
        val receivedMessage = Message(
                streamRoomMessagesResponse.fields.args[0]._id,
                streamRoomMessagesResponse.fields.args[0].rid,
                streamRoomMessagesResponse.fields.args[0].msg,
                Ts(streamRoomMessagesResponse.fields.args[0].ts.date),
                MessageOwner(streamRoomMessagesResponse.fields.args[0].u._id, streamRoomMessagesResponse.fields.args[0].u.username),
                UpdatedAt(streamRoomMessagesResponse.fields.args[0]._updatedAt.date),
                null,
                null
        )
        db.messagesDao().insert(receivedMessage)
        debug("CURRENT Parsed StreamRoomMessagesResponse for room: ${streamRoomMessagesResponse.fields.args[0].rid}")
        debug("Parsed StreamRoomMessagesResponse receivedMessage for room: ${receivedMessage.rid}")
    }
}
