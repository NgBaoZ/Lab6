package com.example.bai3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bai3.activity.AddNoteActivity
import com.example.bai3.activity.NoteDetailActivity
import com.example.bai3.adapter.NoteAdapter
import com.example.bai3.database.DatabaseHelper
import com.example.bai3.model.Note

class MainActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var notesAdapter: NoteAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var buttonAddNote: Button

    companion object {
        const val REQUEST_CODE_ADD_NOTE = 1
        const val REQUEST_CODE_VIEW_NOTE = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databaseHelper = DatabaseHelper(this)

        recyclerView = findViewById(R.id.recycler_view_notes)
        recyclerView.layoutManager = LinearLayoutManager(this)
        notesAdapter = NoteAdapter(mutableListOf()) { note -> onNoteClicked(note) }
        recyclerView.adapter = notesAdapter

        buttonAddNote = findViewById(R.id.button_add_note)
        buttonAddNote.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_ADD_NOTE)
        }

        loadNotes()
    }

    private fun loadNotes() {
        val notes = databaseHelper.getAllNotes()
        notesAdapter.updateNotes(notes)
    }

    private fun onNoteClicked(note: Note) {
        val intent = Intent(this, NoteDetailActivity::class.java)
        intent.putExtra("note_id", note.id)
        startActivityForResult(intent, REQUEST_CODE_VIEW_NOTE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            loadNotes() // Cập nhật danh sách ghi chú
        }
    }
}
