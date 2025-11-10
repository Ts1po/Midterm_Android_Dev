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
    // 1. ცვლადები მდგომარეობის შესანახად
    // თუ ფასი არ გადმოვა, დეფოლტად ვიყენებთ 120.0-ს, როგორც დავალების მაგალითშია
    private var productPrice: Double = 120.0
    private var currentQuantity: Int = 1 // საწყისი რაოდენობა 1

    // 2. View-ების დეკლარირება
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
        receiveDataFromIntent() // იღებს ფასს ProductActivity-დან
        updateUI()             // აყენებს საწყის ფასს და ტოტალს (120$ * 1)
        setupListeners()       // ამუშავებს ღილაკების დაჭერას
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

    // A) ინფორმაციის მიღება Product ეკრანიდან
    private fun receiveDataFromIntent() {
        // ვიყენებთ ProductActivity-ში განსაზღვრულ გასაღებს (EXTRA_PRODUCT_PRICE)
        // ვიღებთ Double ტიპის მონაცემს.
        val receivedPrice = intent.getDoubleExtra(product.EXTRA_PRODUCT_PRICE, 120.0) // 120.0 დეფოლტ ფასად

        if (receivedPrice > 0.0) {
            productPrice = receivedPrice
        }
    }

    private fun calculateTotal(): Double {
        // მთლიანი თანხის გამოთვლა: ფასი * რაოდენობა
        return productPrice * currentQuantity
    }

    private fun updateUI() {
        // განაახლებს ეკრანზე ნაჩვენებ ტექსტებს

        // პროდუქტის ერთეულის ფასის განახლება
        productPriceTextView.text = "${productPrice.toMoneyFormat()}$"

        // რაოდენობის განახლება
        quantityTextView.text = currentQuantity.toString()

        // მთლიანი თანხის განახლება (მაგ. 120$ * 3 = 360$)
        val total = calculateTotal()
        totalTextView.text = "${total.toMoneyFormat()} $"
    }

    // დამხმარე ფუნქცია, რომ ფასი გამოჩნდეს მთელი რიცხვით, თუ არ აქვს წილადი ნაწილი
    private fun Double.toMoneyFormat(): String {
        return if (this == this.toInt().toDouble()) {
            this.toInt().toString()
        } else {
            String.format("%.2f", this)
        }
    }

    private fun setupListeners() {
        // "+" ღილაკი: რაოდენობის გაზრდა
        plusButton.setOnClickListener {
            currentQuantity++ // გაზრდა 1-ით
            updateUI()        // გადათვლა და UI განახლება
        }

        // "-" ღილაკი: რაოდენობის შემცირება
        minusButton.setOnClickListener {
            if (currentQuantity > 1) { // რაოდენობა არ უნდა ჩამოვიდეს 1-ზე ნაკლებზე
                currentQuantity--      // შემცირება 1-ით
                updateUI()             // გადათვლა და UI განახლება
            } else {
                Toast.makeText(this, "Minimum quantity is 1.", Toast.LENGTH_SHORT).show()
            }
        }

        // "ისრის ხატულა" (უკან): დაბრუნება Product ეკრანზე
        backButton.setOnClickListener {
            // finish() ხურავს მიმდინარე Activity-ს და ბრუნდება წინა ეკრანზე (ProductActivity)
            finish()
        }

        // "Place Order" ღილაკი: გადასვლა Successfully Pop ეკრანზე
        placeOrderButton.setOnClickListener {
            val intent = Intent(this, SuccessfullyPopActivity::class.java)
            // My Cart -> Successfully Pop (წინ გადასვლა)
            startActivity(intent)
        }
    }
}