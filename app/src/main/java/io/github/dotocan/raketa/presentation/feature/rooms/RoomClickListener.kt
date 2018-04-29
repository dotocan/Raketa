package io.github.dotocan.raketa.presentation.feature.rooms

import io.github.dotocan.raketa.domain.model.websocket.response.Room


/**
 * Created by dotocan on 17.3.2018..
 */

interface RoomClickListener {
    fun onRoomClicked(room: Room)
}