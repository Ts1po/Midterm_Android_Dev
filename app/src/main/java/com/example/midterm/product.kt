package com.example.midterm

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class product : AppCompatActivity() {

    companion object {
        const val EXTRA_PRODUCT_PRICE = "product_price"

        const val PRODUCT_PRICE = 120.0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_product)
        val oldPriceTextView: TextView = findViewById(R.id.old_price_tv)
        oldPriceTextView.paintFlags = oldPriceTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

        val addToCartButton: Button = findViewById(R.id.add_to_cart_button)

        addToCartButton.setOnClickListener {

            val intent = Intent(this, MyCartActivity::class.java)

            intent.putExtra(EXTRA_PRODUCT_PRICE, PRODUCT_PRICE)

            startActivity(intent)
        }
    }
}