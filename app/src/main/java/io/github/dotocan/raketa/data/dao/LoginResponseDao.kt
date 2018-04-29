package io.github.dotocan.raketa.data.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.github.dotocan.raketa.domain.model.websocket.response.LoginResponse

@Dao
interface LoginResponseDao {
    @Query("SELECT * FROM LOGIN_RESPONSES")
    fun getAll(): LiveData<List<LoginResponse>>

    @Insert
    fun insert(loginResponse: LoginResponse)

    @Delete
    fun deleteAll(loginResponses: List<LoginResponse>)
}