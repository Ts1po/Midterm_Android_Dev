package com.example.midterm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MyCartActivity : AppCompatActivity() {
    private var productPrice: Double = 120.0
    private var currentQuantity: Int = 1

    private lateinit var quantityTextView: TextView
    private lateinit var productPriceTextView: TextView
    private lateinit var totalTextView: TextView
    private lateinit var minusButton: ImageButton
    private lateinit var plusButton: ImageButton
    private lateinit var backButton: ImageButton
    private lateinit var placeOrderButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_cart)
        initializeViews()
        receiveDataFromIntent()
        updateUI()
        setupListeners()
    }
    private fun initializeViews() {
        quantityTextView = findViewById(R.id.quantity_tv)
        productPriceTextView = findViewById(R.id.cart_product_price_tv)
        totalTextView = findViewById(R.id.total_price_tv)
        minusButton = findViewById(R.id.minus_button)
        plusButton = findViewById(R.id.plus_button)
        backButton = findViewById(R.id.back_arrow_icon)
        placeOrderButton = findViewById(R.id.place_order_button)
    }

    private fun receiveDataFromIntent() {
        val receivedPrice = intent.getDoubleExtra(product.EXTRA_PRODUCT_PRICE, 120.0)

        if (receivedPrice > 0.0) {
            productPrice = receivedPrice
        }
    }

    private fun calculateTotal(): Double {
        return productPrice * currentQuantity
    }

    private fun updateUI() {

        productPriceTextView.text = "${productPrice.toMoneyFormat()}$"

        quantityTextView.text = currentQuantity.toString()

        val total = calculateTotal()
        totalTextView.text = "${total.toMoneyFormat()} $"
    }

    private fun Double.toMoneyFormat(): String {
        return if (this == this.toInt().toDouble()) {
            this.toInt().toString()
        } else {
            String.format("%.2f", this)
        }
    }

    private fun setupListeners() {
        plusButton.setOnClickListener {
            currentQuantity++
            updateUI()
        }

        minusButton.setOnClickListener {
            if (currentQuantity > 1) {
                currentQuantity--
                updateUI()
            } else {
                Toast.makeText(this, "Minimum quantity is 1.", Toast.LENGTH_SHORT).show()
            }
        }

        backButton.setOnClickListener {
            finish()
        }

        placeOrderButton.setOnClickListener {
            val intent = Intent(this, SuccessfullyPopActivity::class.java)
            startActivity(intent)
        }
    }
}