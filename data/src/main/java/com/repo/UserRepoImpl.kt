package com.repo

import android.util.Log
import com.model.Note
import com.room.NoteDao
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class UserRepoImpl @Inject constructor(
    val noteDao: NoteDao
) : UserRepo {
    override fun getAllNotes(): Single<List<Note>> {
        return noteDao.getAllNotes()
    }

    override fun insert(notes: Note): Completable {
        return noteDao.insert(notes)
    }

    override fun delete(notes: Note): Completable {
        return noteDao.delete(notes)
    }

    override fun update(notes: Note): Completable {
        return noteDao.update(notes)
    }

}