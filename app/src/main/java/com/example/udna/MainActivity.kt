package com.example.udna

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import java.io.File

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val buttonNotes = findViewById<Button>(R.id.notes)
        buttonNotes.setOnClickListener{
            val intent = Intent(this,austinActivity::class.java)
            startActivity(intent)
        }

        val buttonMaps = findViewById<Button>(R.id.maps)
        buttonMaps.setOnClickListener{
            val intent = Intent(this,maps::class.java)
            startActivity(intent)

        }

    }

    fun openCharList(){
        val intent = Intent(this, CharactersList::class.java)
        startActivity(intent)
    }
}