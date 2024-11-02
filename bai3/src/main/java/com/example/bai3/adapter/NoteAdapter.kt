package com.example.bai3.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bai3.R
import com.example.bai3.model.Note

class NoteAdapter(private var notes: List<Note>, private val onClick: (Note) -> Unit) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.text_view_note_title)
        private val contentTextView: TextView = itemView.findViewById(R.id.text_view_note_content)

        fun bind(note: Note) {
            titleTextView.text = note.title
            contentTextView.text = note.content
            itemView.setOnClickListener { onClick(note) } // Gọi hàm khi nhấp vào ghi chú
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    override fun getItemCount() = notes.size

    fun updateNotes(newNotes: List<Note>) {
        notes = newNotes
        notifyDataSetChanged() // Cập nhật lại danh sách
    }
}
