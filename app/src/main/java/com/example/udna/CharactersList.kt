package com.example.udna


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import java.io.File
import kotlin.math.log


class CharactersList : AppCompatActivity() {

    lateinit var adapter: CharacterAdapter
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_characters_list)


        val newChar = findViewById<FloatingActionButton>(R.id.newCharButton)
        val charListView = findViewById<ListView>(R.id.char_list)
        val emptyList = findViewById<TextView>(R.id.empty_list_text)

        newChar.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, CharacterInfo::class.java)
            startActivity(intent)
        })

        val charactersList = redrawList(this,emptyList)

        adapter = CharacterAdapter(this, android.R.layout.simple_list_item_1, charactersList)
        charListView.adapter = adapter
        charListView.setOnItemClickListener { parent, view, position, id ->
            val item = adapter.getView(position,null,parent)
            val intent = Intent(this, CharacterInfo::class.java)
            intent.putExtra("character", charactersList.find{it.id == item.tag})
            intent.putExtra("position", position)
            startActivityForResult(intent, RESULT_OK)
        }
    }
    override fun onResume(){
        super.onResume()
        val emptyList = findViewById<TextView>(R.id.empty_list_text)
        val charactersList = redrawList(this,emptyList)
        adapter.clear()
        adapter.addAll(charactersList)
        adapter.notifyDataSetChanged()
    }

}

fun redrawList(context: Context,emptyList:TextView) : MutableList<DndCharacter>{
    val directory = File(context.getExternalFilesDir(null), "characters")
    val charactersList = buildCharacterList(directory)
    if(charactersList.isEmpty()){
        emptyList.text = "Nobody here but us chickens"
    }else{
        emptyList.text = ""
    }
    return charactersList
}

fun buildCharacterList(directory: File) : MutableList<DndCharacter>{
    val gson = Gson()
    val charactersList = mutableListOf<DndCharacter>()
    val characterFilesList = directory.listFiles()
    for(file in characterFilesList){
        val character = gson.fromJson(file.readText(), DndCharacter::class.java)
        charactersList.add(character)
    }
    return charactersList
}

class CharacterAdapter(context: Context, resource: Int, objects:MutableList<DndCharacter>) : ArrayAdapter<DndCharacter>(context, resource, objects){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.characters_list_layout,parent,false)
        }
        val currentItem = getItem(position)
        val nameText = view?.findViewById<TextView>(R.id.list_item_name)
        val levelText = view?.findViewById<TextView>(R.id.list_item_level)
        val raceText = view?.findViewById<TextView>(R.id.list_item_race_class)
        val strText = view?.findViewById<TextView>(R.id.list_item_str)
        val dexText = view?.findViewById<TextView>(R.id.list_item_dex)
        val conText = view?.findViewById<TextView>(R.id.list_item_con)
        val intText = view?.findViewById<TextView>(R.id.list_item_int)
        val wisText = view?.findViewById<TextView>(R.id.list_item_wis)
        val chaText = view?.findViewById<TextView>(R.id.list_item_cha)

        nameText?.text = currentItem?.name
        levelText?.text = "Level ${currentItem?.level}"
        raceText?.text =  "${currentItem?.race} ${currentItem?.className}"
        strText?.text = currentItem?.abilities?.find { it.name == "Strength" }?.score.toString()
        dexText?.text = currentItem?.abilities?.find { it.name == "Dexterity" }?.score.toString()
        conText?.text = currentItem?.abilities?.find { it.name == "Constitution" }?.score.toString()
        intText?.text = currentItem?.abilities?.find { it.name == "Intelligence" }?.score.toString()
        wisText?.text = currentItem?.abilities?.find { it.name == "Wisdom" }?.score.toString()
        chaText?.text = currentItem?.abilities?.find { it.name == "Charisma" }?.score.toString()

        if (currentItem != null) {
            view?.tag = currentItem.id
        }

        return view!!
    }
}