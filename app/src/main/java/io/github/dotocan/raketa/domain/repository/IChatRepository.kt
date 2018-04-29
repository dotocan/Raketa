package io.github.dotocan.raketa.domain.repository

import io.github.dotocan.raketa.domain.model.websocket.response.Message
import io.reactivex.Flowable
import io.reactivex.Single

interface IChatRepository {
    fun getLatestMessageFor(roomId: String): Single<Message>
    fun getMessagesFor(roomId: String): Flowable<List<Message>>
    fun syncMessages(roomId: String)
    fun streamMessagesFor(roomId: String)
    fun sendMessage(roomId: String, msg: String)
}