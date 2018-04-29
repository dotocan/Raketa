package io.github.dotocan.raketa.data.repository

import io.github.dotocan.raketa.data.service.websocket.WebsocketHandler
import io.github.dotocan.raketa.data.util.randomId
import io.github.dotocan.raketa.db
import io.github.dotocan.raketa.domain.model.websocket.request.LoadHistoryRequest
import io.github.dotocan.raketa.domain.model.websocket.request.SendMessageParam
import io.github.dotocan.raketa.domain.model.websocket.request.SendMessageRequest
import io.github.dotocan.raketa.domain.model.websocket.request.StreamRoomMessagesRequest
import io.github.dotocan.raketa.domain.model.websocket.response.Message
import io.github.dotocan.raketa.domain.model.websocket.response.UpdatedAt
import io.github.dotocan.raketa.domain.repository.IChatRepository
import io.github.dotocan.raketa.util.extensions.debug
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class ChatRepository: IChatRepository {

    /**
     * Gets the latest message from the database
     */
    override fun getLatestMessageFor(roomId: String): Single<Message> {
         return Single.defer { db.messagesDao().getLatestForRoom(roomId) }.subscribeOn(Schedulers.io())
    }

    /**
     * Gets all messages from the database
     */
    override fun getMessagesFor(roomId: String): Flowable<List<Message>> {
        return db.messagesDao().getAllForRoom(roomId)
    }

    /**
     * Gets messages from a server and syncs them with the databse
     */
    override fun syncMessages(roomId: String) {
        var updatedAt: UpdatedAt? = null
        getLatestMessageFor(roomId).map { it -> updatedAt = it._updatedAt }

        val loadHistoryRequest = LoadHistoryRequest("method", "loadHistory",
                randomId(), listOf(roomId, null, 50, updatedAt))

        debug("LOAD HISTORY REQUEST: $loadHistoryRequest")

        WebsocketHandler.websocketService.sendLoadHistoryMessage(loadHistoryRequest)
    }

    /**
     * Subscribes to a selected room to receive message updates.
     */
    override fun streamMessagesFor(roomId: String) {
        val streamRoomMessagesRequest = StreamRoomMessagesRequest("sub", randomId(),
                "stream-room-messages", listOf(roomId, true))

        WebsocketHandler.websocketService.sendStreamRoomMessagesMessage(streamRoomMessagesRequest)
    }

    /**
     * Sends a chat message to a selected room.
     */
    override fun sendMessage(roomId: String, msg: String) {
        val sendMessagesRequest = SendMessageRequest("method", "sendMessage", randomId(),
                listOf(SendMessageParam(randomId(), roomId, msg)))

        WebsocketHandler.websocketService.sendSendMessageRequest(sendMessagesRequest)
    }
}