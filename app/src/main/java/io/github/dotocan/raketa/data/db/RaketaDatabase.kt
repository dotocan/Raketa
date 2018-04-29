package io.github.dotocan.raketa.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import io.github.dotocan.raketa.data.dao.LoginResponseDao
import io.github.dotocan.raketa.data.dao.MesssagesDao
import io.github.dotocan.raketa.data.dao.RoomsDao
import io.github.dotocan.raketa.domain.model.websocket.response.ConnectResponse
import io.github.dotocan.raketa.domain.model.websocket.response.LoginResponse
import io.github.dotocan.raketa.domain.model.websocket.response.Message
import io.github.dotocan.raketa.domain.model.websocket.response.Room

/**
 * Created by dotocan on 3.3.2018..
 */

@Database(entities = [(LoginResponse::class), (Room::class),
    (Message::class)], version = 1)
abstract class RaketaDatabase : RoomDatabase() {
    abstract fun loginResponseDao(): LoginResponseDao
    abstract fun roomsDao(): RoomsDao
    abstract fun messagesDao(): MesssagesDao

    companion object {
        const val NAME = "RaketaDB"
    }
}