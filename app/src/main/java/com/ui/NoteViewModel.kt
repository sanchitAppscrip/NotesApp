package com.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.extensions.toFileFailure
import com.model.Note
import com.model.Resource
import com.room.NoteDao
import com.usecases.UserUseCase
import com.usecases.UserUseCaseImpl
import com.utils.NoteStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val userUseCase: UserUseCaseImpl
) : ViewModel() {
    private var isEdit: Boolean = false
    private val disposables = CompositeDisposable()
    private var note = Note()

    private val allNoteObserver by lazy { MutableLiveData<Resource<List<Note>>>() }
    private val insertNoteObserver by lazy { MutableLiveData<Resource<String>>() }
    private val updateNoteObserver by lazy { MutableLiveData<Resource<String>>() }
    private val deleteNoteObserver by lazy { MutableLiveData<Resource<String>>() }


    fun getAllNotesObserver(): MutableLiveData<Resource<List<Note>>> = allNoteObserver
    fun getInsertNotesObserver(): MutableLiveData<Resource<String>> = insertNoteObserver
    fun getUpdateNotesObserver(): MutableLiveData<Resource<String>> = updateNoteObserver
    fun getDeleteNotesObserver(): MutableLiveData<Resource<String>> = deleteNoteObserver


    fun isEdit(): Boolean {
        return isEdit
    }

    fun setEdit(edit: Boolean) {
        isEdit = edit
    }

    fun getNote():Note{
        return this.note
    }


    fun getAllNotes() {
        disposables.add(userUseCase.getAllNotes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                allNoteObserver.value = Resource.loading()
            }
            .subscribe({ response ->
                Log.d("Viewmodel", "Response Success")
                Log.d("Viewmodel", "Response Success ${response.size}")
                allNoteObserver.value = Resource.success(response)
            }) { throwable ->
                Log.d("Viewmodel", "Response Error")
                allNoteObserver.value = Resource.error(throwable.toFileFailure())
            })
    }

    fun setNoteTitle(title: String) {
        note.title = title
    }

    fun setNote(note: Note) {
        this.note = note
    }

    fun setNoteSubTitle(subTitle: String) {
        note.subtitle = subTitle
    }

    fun setNoteDescription(description: String) {
        note.description = description
    }

    fun saveNote() {
        val date = Calendar.getInstance().time

        val dateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
        val formattedDate: String = dateFormat.format(date)
        this.note.time = formattedDate

        if (isEdit) {
            disposables.add(userUseCase.update(this.note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    updateNoteObserver.value = Resource.loading()
                }
                .subscribe({
                    Log.d("Viewmodel", "Response Success")
                    updateNoteObserver.value = Resource.success()
//                    NoteStatus.updatedNote = this.note
                }) { throwable ->
                    Log.d("Viewmodel", "Response Error")
                    updateNoteObserver.value = Resource.error(throwable.toFileFailure())
                })
        } else {
            disposables.add(userUseCase.insert(this.note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    insertNoteObserver.value = Resource.loading()
                }
                .subscribe({
                    Log.d("Viewmodel", "Response Success")
                    insertNoteObserver.value = Resource.success()
                }) { throwable ->
                    Log.d("Viewmodel", "Response Error")
                    insertNoteObserver.value = Resource.error(throwable.toFileFailure())
                })
        }
    }

    fun deleteNote() {
        disposables.add(userUseCase.delete(this.note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                deleteNoteObserver.value = Resource.loading()
            }
            .subscribe({
                Log.d("Viewmodel", "Response Success")
                deleteNoteObserver.value = Resource.success()
            }) { throwable ->
                Log.d("Viewmodel", "Response Error")
                deleteNoteObserver.value = Resource.error(throwable.toFileFailure())
            })
    }
}