package com.repo


import com.model.Note
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface UserRepo {
    fun getAllNotes(): Single<List<Note>>

    fun insert(notes: Note): Completable

    fun delete(notes: Note): Completable

    fun update(notes: Note): Completable
}