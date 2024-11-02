package com.example.bai2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddContactActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextPhone: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var buttonSave: Button
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)

        editTextName = findViewById(R.id.edit_text_name)
        editTextPhone = findViewById(R.id.edit_text_phone)
        editTextEmail = findViewById(R.id.edit_text_email)
        buttonSave = findViewById(R.id.button_save)
        databaseHelper = DatabaseHelper(this)

        buttonSave.setOnClickListener {
            val name = editTextName.text.toString()
            val phone = editTextPhone.text.toString()
            val email = editTextEmail.text.toString()

            if (name.isNotEmpty() && phone.isNotEmpty() && email.isNotEmpty()) {
                val contact = Contact(0, name, phone, email) // ID sẽ tự động được gán
                databaseHelper.addContact(contact)
                Toast.makeText(this, "Contact added successfully", Toast.LENGTH_SHORT).show()
                setResult(RESULT_OK)
                finish() // Kết thúc Activity
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
