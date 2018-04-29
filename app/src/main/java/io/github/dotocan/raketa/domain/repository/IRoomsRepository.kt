package io.github.dotocan.raketa.domain.repository

import io.github.dotocan.raketa.domain.model.websocket.response.Room
import io.reactivex.Flowable

interface IRoomsRepository {
    fun getRooms(): Flowable<List<Room>>
    fun syncRooms()
}