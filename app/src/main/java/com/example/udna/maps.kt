package com.example.udna

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class maps : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val button = findViewById<Button>(R.id.ForestTest)
        button.setOnClickListener{
            val intent = Intent(this,map_content::class.java)
            startActivity(intent)
        }

    }
}