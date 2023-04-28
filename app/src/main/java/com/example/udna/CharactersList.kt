package com.example.udna


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import java.io.File


class CharactersList : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_characters_list)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val newChar = findViewById<FloatingActionButton>(R.id.newCharButton)
        val charListView = findViewById<ListView>(R.id.char_list)
        val emptyList = findViewById<TextView>(R.id.empty_list_text)

        newChar.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, CharacterInfo::class.java)
            intent.putExtra("newChar",1)
            startActivity(intent)
        })

        val gson = Gson()
        val charactersJson = File(this.getExternalFilesDir(null), "characters.json").readText()
        val charactersList = gson.fromJson(charactersJson, Array<DndCharacter>::class.java)
        if(charactersList.isEmpty()){
            emptyList.text = "Nobody here but us chickens"
        }else{
            emptyList.text = ""
        }

        val adapter = CharacterAdapter(this, android.R.layout.simple_list_item_1, charactersList)
        charListView.adapter = adapter
        charListView.setOnItemClickListener { parent, view, position, id ->
            val name = findViewById<TextView>(R.id.list_item_name)
            val intent = Intent(this, CharacterInfo::class.java)
            intent.putExtra("name", name.text)
            intent.putExtra("position", position)
            startActivity(intent)
        }
    }
}

class CharacterAdapter(context: Context, resource: Int, objects: Array<DndCharacter>) : ArrayAdapter<DndCharacter>(context, resource, objects){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.characters_list_layout,parent,false)
        }
        val currentItem = getItem(position)
        val nameText = view?.findViewById<TextView>(R.id.list_item_name)
        val levelText = view?.findViewById<TextView>(R.id.list_item_level)
        val raceText = view?.findViewById<TextView>(R.id.list_item_race_class)

        nameText?.text = currentItem?.name
        levelText?.text = "Level ${currentItem?.level}"
        raceText?.text =  "${currentItem?.race} ${currentItem?.className}"

        return view!!
    }
}