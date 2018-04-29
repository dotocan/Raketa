package io.github.dotocan.raketa.domain.model.websocket.response

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "CONNECT_RESPONSES")
data class ConnectResponse(
        @PrimaryKey val session: String,
        val msg: String
)