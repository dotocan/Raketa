package io.github.dotocan.raketa.data.dao

import android.arch.persistence.room.*
import io.github.dotocan.raketa.domain.model.websocket.response.Room
import io.reactivex.Flowable

@Dao
interface RoomsDao {
    @Query("SELECT * FROM ROOMS")
    fun getAll(): Flowable<List<Room>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(room: Room)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(rooms: List<Room>)

    @Delete
    fun delete(room: Room)

    @Delete
    fun deleteAll(rooms: List<Room>)
}