package com.example.udna

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.io.File

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val folder = File(getExternalFilesDir(null), "characters")
        if(!folder.exists()){
            folder.mkdir()
        }

    }

    fun openCharList(){
        val intent = Intent(this, CharactersList::class.java)
        startActivity(intent)
    }
}