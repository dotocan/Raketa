package io.github.dotocan.raketa.domain.model.websocket.response

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * https://rocket.chat/docs/developer-guides/realtime-api/the-message-object/
 */
@Entity(tableName = "MESSAGES")
data class Message(
        @SerializedName("_id") @PrimaryKey val messageId: String,
        val rid: String,
        val msg: String,
        @Embedded val ts: Ts,
        @SerializedName("u") @Embedded val messageOwner: MessageOwner,
        @Embedded val _updatedAt: UpdatedAt?,
        @Embedded val editedAt: EditedAt?,
        @Embedded val editedBy: EditedBy?
)

data class MessageOwner(
        @SerializedName("_id") val ownerId: String,
		@SerializedName("username") val ownerUsername: String
)

data class Ts(
		@SerializedName("\$date") val tsDate: Long
)

data class UpdatedAt(
        @SerializedName("\$date") val updatedAtDate: Long
)

data class EditedAt(
        @SerializedName("\$date") val editedAtDate: Long
)

data class EditedBy(
        @SerializedName("_id") val editorId: String,
        @SerializedName("username") val editorUsername: String
)
