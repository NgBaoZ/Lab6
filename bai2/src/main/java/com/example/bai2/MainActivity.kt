package com.example.bai2

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var buttonAdd: Button
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var contacts: List<Contact>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.list_view_contacts)
        buttonAdd = findViewById(R.id.button_add)
        databaseHelper = DatabaseHelper(this)

        loadContacts()

        buttonAdd.setOnClickListener {
            val intent = Intent(this, AddContactActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_ADD_CONTACT)
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedContact = contacts[position]
            val intent = Intent(this, ContactDetailActivity::class.java)
            intent.putExtra("contact_id", selectedContact.id) // Chuyển ID liên hệ
            startActivityForResult(intent, REQUEST_CODE_VIEW_CONTACT) // Mở ContactDetailActivity
        }
    }

    private fun loadContacts() {
        contacts = databaseHelper.getAllContacts() // Lấy lại danh bạ từ cơ sở dữ liệu
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, contacts.map { it.name })
        listView.adapter = adapter // Cập nhật ListView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            loadContacts() // Gọi lại loadContacts để cập nhật danh sách
        }
    }

    companion object {
        const val REQUEST_CODE_ADD_CONTACT = 1
        const val REQUEST_CODE_VIEW_CONTACT = 2
    }
}
