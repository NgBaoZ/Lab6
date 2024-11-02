package com.example.bai3.activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.bai3.R
import com.example.bai3.database.DatabaseHelper
import com.example.bai3.model.Note
import java.util.*

class AddNoteActivity : AppCompatActivity() {

    private lateinit var editTextTitle: EditText
    private lateinit var editTextContent: EditText
    private lateinit var buttonSave: Button
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        editTextTitle = findViewById(R.id.edit_text_title)
        editTextContent = findViewById(R.id.edit_text_content)
        buttonSave = findViewById(R.id.button_save)
        databaseHelper = DatabaseHelper(this)

        buttonSave.setOnClickListener {
            val title = editTextTitle.text.toString()
            val content = editTextContent.text.toString()
            val createdAt = Date()
            val note = Note(0, title, content, createdAt, false)
            databaseHelper.addNote(note)
            setResult(RESULT_OK) // Đặt kết quả khi lưu thành công
            finish() // Kết thúc Activity
        }
    }
}
