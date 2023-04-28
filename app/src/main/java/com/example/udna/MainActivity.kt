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
        val file = File(getExternalFilesDir(null), "characters.json")
        if(file.createNewFile()){
            file.writeText("[\n]")
        }

    }

    fun openCharList(){
        val intent = Intent(this, CharactersList::class.java)
        startActivity(intent)
    }
}