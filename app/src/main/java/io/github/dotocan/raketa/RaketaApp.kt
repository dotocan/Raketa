package io.github.dotocan.raketa

import android.app.Application
import android.arch.persistence.room.Room
import android.content.SharedPreferences
import io.github.dotocan.raketa.data.db.RaketaDatabase
import org.jetbrains.anko.defaultSharedPreferences

/**
 * Created by dotocan on 12.3.2018..
 */

val db by lazy { RaketaApp.database }
val sharedPrefs by lazy {RaketaApp.sharedPreferences}

class RaketaApp : Application() {

    companion object {
        lateinit var database: RaketaDatabase
        lateinit var sharedPreferences: SharedPreferences
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, RaketaDatabase::class.java, RaketaDatabase.NAME)
                .build()
        sharedPreferences = defaultSharedPreferences
    }
}