package com.example.bai2

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ContactDetailActivity : AppCompatActivity() {

    private lateinit var textViewName: TextView
    private lateinit var textViewPhone: TextView
    private lateinit var textViewEmail: TextView
    private lateinit var buttonDelete: Button
    private lateinit var buttonCall: Button
    private lateinit var buttonEmail: Button
    private lateinit var databaseHelper: DatabaseHelper
    private var contactId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_detail)

        textViewName = findViewById(R.id.text_view_name)
        textViewPhone = findViewById(R.id.text_view_phone)
        textViewEmail = findViewById(R.id.text_view_email)
        buttonDelete = findViewById(R.id.button_delete)
        buttonCall = findViewById(R.id.button_call)
        buttonEmail = findViewById(R.id.button_email)

        databaseHelper = DatabaseHelper(this)

        contactId = intent.getLongExtra("contact_id", 0)
        val contact = databaseHelper.getContactById(contactId)

        textViewName.text = contact.name
        textViewPhone.text = contact.phone
        textViewEmail.text = contact.email

        buttonDelete.setOnClickListener {
            val result = databaseHelper.deleteContact(contactId) // Xóa liên hệ
            if (result) {
                setResult(RESULT_OK) // Chỉ đặt kết quả nếu xóa thành công
            }
            finish() // Kết thúc Activity chi tiết
        }

        buttonCall.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${contact.phone}")
            startActivity(intent)
        }

        buttonEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:${contact.email}")
            startActivity(intent)
        }
    }
}
