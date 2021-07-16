package com.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.extensions.shortToast
import com.model.Note
import com.model.Status
import com.test.notesapp.R
import com.test.notesapp.databinding.ActivityEditNotesBinding
import com.utils.AppConstants
import com.utils.NoteStatus
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EditNotesActivity : AppCompatActivity() {
    companion object {
        private const val NOTE_DETAILS = "NOTE_DETAILS"
        fun getStartIntent(note: Note, context: Context): Intent {
            val intent = Intent(context, EditNotesActivity::class.java)
            intent.putExtra(NOTE_DETAILS, note)
            return intent
        }
    }

    private var note = Note()
    private lateinit var binding: ActivityEditNotesBinding
    private lateinit var viewModel: NoteViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataBinding()
        setListener()
        setObservers()
    }

    private fun initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_notes)
        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        var toolbarTitle = getString(R.string.activity_edit_note_title_create)

        intent.getParcelableExtra<Note>(NOTE_DETAILS)?.let {
            note = it
            viewModel.setEdit(true)
            viewModel.setNote(note)
            toolbarTitle = getString(R.string.activity_edit_note_title_edit)

        }

        binding.note = note
        binding.toolbar.title = toolbarTitle

    }


    private fun setListener() {
        binding.ivDelete.setOnClickListener {
            showConfirmationDialog()
        }
    }

    private fun setObservers() {
        viewModel.getInsertNotesObserver().observe(this, Observer { resource ->
            when (resource.status) {
                Status.LOADING -> {

                }

                Status.SUCCESS -> {
                    NoteStatus.value = AppConstants.NOTE_ADDED
                    val returnIntent = Intent()
                    setResult(Activity.RESULT_CANCELED, returnIntent)
                    shortToast(getString(R.string.msg_note_created))
                    finish()
                }

                Status.ERROR -> {
                    NoteStatus.value = AppConstants.NOTE_UNCHANGED
                    shortToast(getString(R.string.msg_error_occured))
                }
            }
        })

        viewModel.getUpdateNotesObserver().observe(this, Observer { resource ->
            when (resource.status) {
                Status.LOADING -> {

                }

                Status.SUCCESS -> {
                    NoteStatus.value = AppConstants.NOTE_UPDATED
                    val returnIntent = Intent()
                    val updatedNote = viewModel.getNote()
                    returnIntent.putExtra(AppConstants.RESULT, updatedNote)
                    setResult(RESULT_OK, returnIntent)
                    shortToast(getString(R.string.msg_note_updated))
                    finish()
                }

                Status.ERROR -> {
                    NoteStatus.value = AppConstants.NOTE_UNCHANGED
                    shortToast(getString(R.string.msg_error_occured))
                }
            }
        })

        viewModel.getDeleteNotesObserver().observe(this, Observer { resource ->
            when (resource.status) {
                Status.LOADING -> {

                }

                Status.SUCCESS -> {
                    NoteStatus.value = AppConstants.NOTE_DELETED
                    val returnIntent = Intent()
                    setResult(Activity.RESULT_CANCELED, returnIntent)
                    shortToast(getString(R.string.msg_note_deleted))
                    finish()
                }

                Status.ERROR -> {
                    NoteStatus.value = AppConstants.NOTE_UNCHANGED
                    shortToast(getString(R.string.msg_error_occured))
                }
            }
        })
    }

    private fun showConfirmationDialog() {
        val dialogBuilder = AlertDialog.Builder(this)

        dialogBuilder.setTitle(getString(R.string.dialog_title_confirm_delete))
        dialogBuilder.setMessage(getString(R.string.dialog_msg_sure_want_to_delete))

        dialogBuilder.setPositiveButton(
            getString(R.string.yes)
        ) { dialog, _ ->
            dialog.dismiss()
            viewModel.deleteNote()
        }

        dialogBuilder.setNegativeButton(
            getString(R.string.no)
        ) { dialog, which -> // Do nothing
            dialog.dismiss()
        }

        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }
}