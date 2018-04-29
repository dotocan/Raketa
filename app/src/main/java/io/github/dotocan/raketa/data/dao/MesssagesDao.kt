package io.github.dotocan.raketa.data.dao

import android.arch.persistence.room.*
import io.github.dotocan.raketa.domain.model.websocket.response.Message
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface MesssagesDao {

    @Query("SELECT * FROM MESSAGES WHERE rid LIKE :rid ORDER BY tsDate ASC")
    fun getAllForRoom(rid: String): Flowable<List<Message>>

    @Query("SELECT * FROM MESSAGES WHERE rid LIKE :rid ORDER BY tsDate DESC LIMIT 1")
    fun getLatestForRoom(rid: String): Single<Message>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(message: Message)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(messages: List<Message>)

    @Delete
    fun delete(message: Message)

    @Delete
    fun deleteAll(messages: List<Message>)
}