package com.usecases

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.model.Note
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single


interface UserUseCase {
    fun getAllNotes(): Single<List<Note>>

    fun insert(notes: Note): Completable

    fun delete(notes: Note): Completable

    fun update(notes: Note): Completable

}