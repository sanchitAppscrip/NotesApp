package com.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.model.Note

@Database(entities = [Note::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}