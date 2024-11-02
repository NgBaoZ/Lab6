package com.example.bai1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class EditProductActivity : AppCompatActivity() {
    private lateinit var productDatabaseHelper: ProductDatabaseHelper
    private lateinit var editTextName: EditText
    private lateinit var editTextPrice: EditText
    private lateinit var editTextDescription: EditText
    private var productId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_product)

        productDatabaseHelper = ProductDatabaseHelper(this)

        editTextName = findViewById(R.id.editTextName)
        editTextPrice = findViewById(R.id.editTextPrice)
        editTextDescription = findViewById(R.id.editTextDescription)

        productId = intent.getIntExtra("PRODUCT_ID", 0)
        val product = productDatabaseHelper.getAllProducts().find { it.id == productId }

        product?.let {
            editTextName.setText(it.name)
            editTextPrice.setText(it.price.toString())
            editTextDescription.setText(it.description)
        }

        val buttonUpdate: Button = findViewById(R.id.buttonUpdate)
        buttonUpdate.setOnClickListener {
            updateProduct()
        }
    }

    private fun updateProduct() {
        val name = editTextName.text.toString()
        val price = editTextPrice.text.toString().toDoubleOrNull() ?: 0.0
        val description = editTextDescription.text.toString()

        productDatabaseHelper.updateProduct(productId, name, price, description)
        setResult(RESULT_OK)
        finish()
    }
}
