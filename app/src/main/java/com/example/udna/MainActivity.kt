package com.example.udna

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import java.io.File

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        val folder = File(getExternalFilesDir(null), "characters")
        if(!folder.exists()) {
            folder.mkdir()
        }

        val folderN = File(getExternalFilesDir(null), "notes")
        if(!folderN.exists()) {
            folderN.mkdir()
            }

        val mapsfolder = File(getExternalFilesDir(null), "maps")
        if(!mapsfolder.exists()) {
            mapsfolder.mkdir()

        }

        val buttonNotes = findViewById<Button>(R.id.notes)
        buttonNotes.setOnClickListener{
            val intent = Intent(this,austinActivity::class.java)
            startActivity(intent)
        }

        val buttonMaps = findViewById<Button>(R.id.map)
        buttonMaps.setOnClickListener{
            val intent = Intent(this,maps::class.java)
            startActivity(intent)
        }

        val buttonCharacters = findViewById<Button>(R.id.characters)
        buttonCharacters.setOnClickListener{
            val intent = Intent(this, CharactersList::class.java)
            startActivity(intent)
        }
    }
}