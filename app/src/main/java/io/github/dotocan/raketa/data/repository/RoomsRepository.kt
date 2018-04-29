package io.github.dotocan.raketa.data.repository

import io.github.dotocan.raketa.data.service.websocket.WebsocketHandler
import io.github.dotocan.raketa.data.util.randomId
import io.github.dotocan.raketa.db
import io.github.dotocan.raketa.domain.model.websocket.request.GetRoomsParam
import io.github.dotocan.raketa.domain.model.websocket.request.GetRoomsRequest
import io.github.dotocan.raketa.domain.model.websocket.response.Room
import io.github.dotocan.raketa.domain.repository.IRoomsRepository
import io.reactivex.Flowable

/**
 * Created by dotocan on 4.3.2018..
 */
class RoomsRepository : IRoomsRepository {

    override fun getRooms(): Flowable<List<Room>> {
        return db.roomsDao().getAll()
    }

    override fun syncRooms() {
        val getRoomsRequest = GetRoomsRequest("method", "rooms/get", randomId(), listOf(
                GetRoomsParam(0)
        ))

        WebsocketHandler.websocketService.sendGetRoomsMessage(getRoomsRequest)
    }
}