package com.example.udna

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonNotes = findViewById<Button>(R.id.notes)
        buttonNotes.setOnClickListener{
            val intent = Intent(this,CharactersList::class.java)
            startActivity(intent)
        }

        val buttonMaps = findViewById<Button>(R.id.maps)
        buttonMaps.setOnClickListener{
            val intent = Intent(this,maps::class.java)
            startActivity(intent)
        }

        val buttonCharacters = findViewById<Button>(R.id.characters)
        buttonCharacters.setOnClickListener{
            val intent = Intent(this,CharactersList::class.java)
            startActivity(intent)
        }

    }
}