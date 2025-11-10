package com.example.midterm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val startShoppingButton: Button = findViewById(R.id.start_shopping_button)

        startShoppingButton.setOnClickListener {
            val intent = Intent(this, product::class.java)
            startActivity(intent)
        }
    }
}