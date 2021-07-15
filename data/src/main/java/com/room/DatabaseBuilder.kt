package com.room

import android.content.Context
import androidx.room.Room
import javax.inject.Inject

class DatabaseBuilder @Inject constructor(
    val context: Context
) {
    private var INSTANCE: AppDatabase? = null

    fun getInstance(): AppDatabase {
        if (INSTANCE == null) {
            synchronized(AppDatabase::class) {
                INSTANCE = buildRoomDB(context)
            }
        }
        return INSTANCE!!
    }

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "notesApp_note"
        ).build()

}