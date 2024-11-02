package com.example.bai3.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.bai3.R
import com.example.bai3.database.DatabaseHelper

class NoteDetailActivity : AppCompatActivity() {

    private lateinit var textViewTitle: TextView
    private lateinit var textViewContent: TextView
    private lateinit var buttonDelete: Button
    private lateinit var buttonEdit: Button
    private lateinit var databaseHelper: DatabaseHelper
    private var noteId: Long = 0

    companion object {
        const val REQUEST_CODE_EDIT_NOTE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detail)

        textViewTitle = findViewById(R.id.text_view_title)
        textViewContent = findViewById(R.id.text_view_content)
        buttonDelete = findViewById(R.id.button_delete)
        buttonEdit = findViewById(R.id.button_edit)

        databaseHelper = DatabaseHelper(this)

        noteId = intent.getLongExtra("note_id", 0)
        loadNoteDetails()

        buttonEdit.setOnClickListener {
            val intent = Intent(this, EditNoteActivity::class.java)
            intent.putExtra("note_id", noteId)
            startActivityForResult(intent, REQUEST_CODE_EDIT_NOTE)
        }

        buttonDelete.setOnClickListener {
            databaseHelper.deleteNote(noteId)
            setResult(RESULT_OK) // Đặt kết quả để MainActivity biết
            finish()
        }
    }

    private fun loadNoteDetails() {
        val note = databaseHelper.getNoteById(noteId)
        textViewTitle.text = note.title
        textViewContent.text = note.content
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_EDIT_NOTE && resultCode == RESULT_OK) {
            loadNoteDetails() // Cập nhật lại chi tiết ghi chú
            setResult(RESULT_OK) // Đặt kết quả để MainActivity cập nhật danh sách
        }
    }
}
