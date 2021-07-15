package com.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.model.Note
import com.test.notesapp.R
import com.test.notesapp.databinding.ItemNoteBinding
import com.utils.SelectionChangedPayload


class NoteAdapter(): RecyclerView.Adapter<NoteAdapter.MyViewHolder>() {
    private var  noteList: ArrayList<Note>? = null
    lateinit var callback:Callback
    constructor(noteList:ArrayList<Note>, callback: Callback) : this() {
        this.noteList = noteList
        this.callback = callback
    }

    class MyViewHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
//         val binding: ItemNoteBinding = binding
        fun bind(note: Note) {
            binding.note = note
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ItemNoteBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_note, parent, false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val note: Note? = noteList?.get(position)
        if (note != null) {
            holder.bind(note)
            holder.binding.itemClickListener = callback
            setNoteBackground(holder,position)
        }

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        if (payloads.firstOrNull() is SelectionChangedPayload) {
            val note: Note? = noteList?.get(position)
            if (note != null) {
                holder.bind(note)
                holder.binding.itemClickListener = callback
                setNoteBackground(holder,position)
            }
        }
    }

    private fun setNoteBackground(holder: MyViewHolder,position: Int) {
        val a=0
        val b= 1
        val c= 2
        if ((position==a) || ((position-a)%4==0) ){
            holder.binding.divider.setBackgroundColor(ContextCompat.getColor(holder.itemView.context,R.color.red))
            holder.binding.card.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context,R.color.light_red))
        }else if((position==b) || ((position-b)%4==0)){
            holder.binding.divider.setBackgroundColor(ContextCompat.getColor(holder.itemView.context,R.color.yellow))
            holder.binding.card.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context,R.color.light_yellow))
        }else if((position==c) || ((position-c)%4==0)){
            holder.binding.divider.setBackgroundColor(ContextCompat.getColor(holder.itemView.context,R.color.blue))
            holder.binding.card.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context,R.color.light_blue))
        }else{
            holder.binding.divider.setBackgroundColor(ContextCompat.getColor(holder.itemView.context,R.color.green))
            holder.binding.card.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context,R.color.light_green))
        }
    }

    override fun getItemCount(): Int {
       return noteList?.size?:0
    }

    interface Callback{
        fun onNoteClickedListener(note:Note)
    }

    fun addItem(note: Note){
        noteList?.add(note)
    }

    fun clear(){
        noteList?.clear()
        notifyDataSetChanged()
    }

    fun removeItem(note: Note){
        val position = findPosition(note)
         noteList?.remove(note)
            position?.let { notifyItemRemoved(it) }

    }

    fun changeNoteDetails(note: Note){
        var noteToUpdate = noteList?.find { noteItem -> noteItem.id == note.id }
        var position = noteToUpdate?.let { findPosition(it) }

        if (position != null) {
            noteList?.set(position, note)
        }
        position?.let { notifyItemChanged(it, SelectionChangedPayload)
        }
    }

    fun findPosition(note: Note):Int?{
        var position: Int? = null
        noteList?.forEachIndexed { index, noteItem ->
            if (noteItem == note){
                position = index
            }
        }
        return position
    }


}
