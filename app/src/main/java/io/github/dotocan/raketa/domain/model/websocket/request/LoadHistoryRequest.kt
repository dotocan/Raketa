package io.github.dotocan.raketa.domain.model.websocket.request

/**
 * https://rocket.chat/docs/developer-guides/realtime-api/method-calls/load-history/
 */
data class LoadHistoryRequest(
		val msg: String,
		val method: String,
		val id: String,
		val params: List<Any?>
)


/**
This method accepts 4 parameters in the following order:

The room id
The oldest message timestamp date (or null) - this is used to do pagination
The message quantity
A date object - the date of the last time the client got data for the room
 */

// "params": [ "room-id", { "$date": 1480377205 }, 50, { "$date": 1480377601 } ]
data class LoadHistoryParam (
        val roomId: String,
        val oldestMessageDate: Long,
        val messageQuantity: String,
        val lastUpdated: Long
)


