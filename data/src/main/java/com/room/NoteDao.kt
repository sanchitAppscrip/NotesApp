package com.room

import androidx.room.*
import com.model.Note
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface NoteDao {
    @Query("SELECT * FROM note ORDER BY id DESC")
    fun getAllNotes(): Single<List<Note>>

    @Insert
    fun insert(notes: Note): Completable

    @Delete
    fun delete(notes: Note): Completable

    @Update
    fun update(notes: Note): Completable

//    @Query("SELECT EXISTS(SELECT * FROM note WHERE name = :name)")
//    suspend fun doesPlayerExist(name: String): Boolean
}