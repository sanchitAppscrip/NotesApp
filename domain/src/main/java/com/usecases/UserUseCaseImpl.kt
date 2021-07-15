package com.usecases


import com.model.Note
import com.repo.UserRepoImpl
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class UserUseCaseImpl @Inject constructor(
    private val userRepo: UserRepoImpl
) : UserUseCase {
    override fun getAllNotes(): Single<List<Note>> {
        return userRepo.getAllNotes()
    }

    override fun insert(notes: Note): Completable {
        return userRepo.insert(notes)
    }

    override fun delete(notes: Note): Completable {
        return userRepo.delete(notes)
    }

    override fun update(notes: Note): Completable {
        return userRepo.update(notes)
    }


}