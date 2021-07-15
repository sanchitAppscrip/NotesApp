package com.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.extensions.shortToast
import com.model.Note
import com.model.Status
import com.test.notesapp.R
import com.test.notesapp.databinding.ActivityNotesListBinding
import com.ui.adapter.NoteAdapter
import com.utils.AppConstants
import com.utils.NoteStatus
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesListActivity : AppCompatActivity(), NoteAdapter.Callback {
    companion object{
        private const val FLIPPER_CHILD_LIST = 0
        private const val FLIPPER_CHILD_LOADING = 1
        private const val FLIPPER_CHILD_NO_RESULTS = 2
    }
    private lateinit var adapter: NoteAdapter
    private lateinit var binding: ActivityNotesListBinding
    private lateinit var viewModel: NoteViewModel
    private lateinit var selectedNote: Note

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataBinding()
        setListener()
        setObserver()
    }

    private fun initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notes_list)
        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        binding.lifecycleOwner = this
        viewModel.getAllNotes()
    }

    private fun setListener() {
        binding.fab.setOnClickListener {
            startActivity(Intent(this, EditNotesActivity::class.java))
        }
    }


    private fun setObserver() {
        viewModel.getAllNotesObserver().observe(this, Observer { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    binding.viewFlipper.displayedChild = FLIPPER_CHILD_LOADING
                    binding.fab.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    binding.fab.visibility = View.VISIBLE
                        setAdapter(resource.data)
                }
                else -> {
                    shortToast(getString(R.string.msg_error_occured))
                }
            }
        })
    }

    private fun setAdapter(list: List<Note>?) {
        if (list==null || list.isEmpty()){
            binding.viewFlipper.displayedChild = FLIPPER_CHILD_NO_RESULTS
        }else{
            binding.viewFlipper.displayedChild = FLIPPER_CHILD_LIST

            val list = ArrayList(list)

            adapter = NoteAdapter(list, this)
            binding.rvNotes.adapter = adapter
        }

    }

    override fun onResume() {
        super.onResume()
        when (NoteStatus.value) {
            AppConstants.NOTE_ADDED -> {
                if(this::adapter.isInitialized){
                    adapter.clear()
                }
                viewModel.getAllNotes()
            }
            AppConstants.NOTE_DELETED -> {
                val position = adapter.findPosition(selectedNote)
                adapter.removeItem(selectedNote)
                position?.let { adapter.notifyItemRangeChanged(it, adapter.itemCount) }
                if (adapter.itemCount==0){
                    binding.viewFlipper.displayedChild = FLIPPER_CHILD_NO_RESULTS
                }
            }
            AppConstants.NOTE_UPDATED -> {
                adapter.changeNoteDetails(NoteStatus.updatedNote)
                adapter.findPosition(selectedNote)?.let { adapter.notifyItemChanged(it) }
            }
        }
    }

    override fun onNoteClickedListener(note: Note) {
        NoteStatus.value = AppConstants.NOTE_UNCHANGED
        selectedNote = note
        startActivity(EditNotesActivity.getStartIntent(note, this))
    }
}