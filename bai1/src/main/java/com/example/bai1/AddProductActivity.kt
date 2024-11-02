package com.example.bai1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddProductActivity : AppCompatActivity() {
    private lateinit var productDatabaseHelper: ProductDatabaseHelper
    private lateinit var editTextName: EditText
    private lateinit var editTextPrice: EditText
    private lateinit var editTextDescription: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        productDatabaseHelper = ProductDatabaseHelper(this)

        editTextName = findViewById(R.id.editTextName)
        editTextPrice = findViewById(R.id.editTextPrice)
        editTextDescription = findViewById(R.id.editTextDescription)

        val buttonSave: Button = findViewById(R.id.buttonSave)
        buttonSave.setOnClickListener {
            addProduct()
        }
    }

    private fun addProduct() {
        val name = editTextName.text.toString()
        val price = editTextPrice.text.toString().toDoubleOrNull() ?: 0.0
        val description = editTextDescription.text.toString()

        productDatabaseHelper.addProduct(name, price, description)
        setResult(RESULT_OK)
        finish()
    }
}
