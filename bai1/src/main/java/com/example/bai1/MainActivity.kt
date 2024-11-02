package com.example.bai1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var productDatabaseHelper: ProductDatabaseHelper
    private lateinit var productAdapter: ProductAdapter
    private lateinit var productRecyclerView: RecyclerView
    private lateinit var editTextMinPrice: EditText
    private lateinit var editTextMaxPrice: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        productDatabaseHelper = ProductDatabaseHelper(this)
        productRecyclerView = findViewById(R.id.productRecyclerView)
        editTextMinPrice = findViewById(R.id.editTextMinPrice)
        editTextMaxPrice = findViewById(R.id.editTextMaxPrice)

        val addProductButton: Button = findViewById(R.id.addProductButton)
        addProductButton.setOnClickListener {
            val intent = Intent(this, AddProductActivity::class.java)
            startActivityForResult(intent, ADD_PRODUCT_REQUEST)
        }

        val buttonFilter: Button = findViewById(R.id.buttonFilter)
        buttonFilter.setOnClickListener {
            filterProducts()
        }

        refreshProductList()
    }

    private fun filterProducts() {
        val minPrice = editTextMinPrice.text.toString().toDoubleOrNull() ?: 0.0
        val maxPrice = editTextMaxPrice.text.toString().toDoubleOrNull() ?: Double.MAX_VALUE

        val filteredProducts = productDatabaseHelper.getProductsByPriceRange(minPrice, maxPrice)

        productAdapter = ProductAdapter(filteredProducts, { productId -> deleteProduct(productId) }) { productId ->
            val intent = Intent(this, EditProductActivity::class.java)
            intent.putExtra("PRODUCT_ID", productId)
            startActivityForResult(intent, EDIT_PRODUCT_REQUEST)
        }
        productRecyclerView.layoutManager = LinearLayoutManager(this)
        productRecyclerView.adapter = productAdapter
    }

    private fun refreshProductList() {
        val products = productDatabaseHelper.getAllProducts()
        productAdapter = ProductAdapter(products, { productId -> deleteProduct(productId) }) { productId ->
            val intent = Intent(this, EditProductActivity::class.java)
            intent.putExtra("PRODUCT_ID", productId)
            startActivityForResult(intent, EDIT_PRODUCT_REQUEST)
        }
        productRecyclerView.layoutManager = LinearLayoutManager(this)
        productRecyclerView.adapter = productAdapter
    }

    private fun deleteProduct(productId: Int) {
        productDatabaseHelper.deleteProduct(productId)
        refreshProductList()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            refreshProductList()
        }
    }

    companion object {
        const val ADD_PRODUCT_REQUEST = 1
        const val EDIT_PRODUCT_REQUEST = 2
    }
}
