package com.example.bai3.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.bai3.R
import com.example.bai3.database.DatabaseHelper
import com.example.bai3.model.Note

class EditNoteActivity : AppCompatActivity() {

    private lateinit var editTextTitle: EditText
    private lateinit var editTextContent: EditText
    private lateinit var buttonUpdate: Button
    private lateinit var databaseHelper: DatabaseHelper
    private var noteId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        editTextTitle = findViewById(R.id.edit_text_title)
        editTextContent = findViewById(R.id.edit_text_content)
        buttonUpdate = findViewById(R.id.button_update)
        databaseHelper = DatabaseHelper(this)

        noteId = intent.getLongExtra("note_id", 0)
        val note = databaseHelper.getNoteById(noteId)

        editTextTitle.setText(note.title)
        editTextContent.setText(note.content)

        buttonUpdate.setOnClickListener {
            val updatedNote = Note(noteId, editTextTitle.text.toString(), editTextContent.text.toString(), note.createdAt, note.isImportant)
            databaseHelper.updateNote(updatedNote)
            setResult(RESULT_OK) // Đặt kết quả để NoteDetailActivity cập nhật
            finish()
        }
    }
}
