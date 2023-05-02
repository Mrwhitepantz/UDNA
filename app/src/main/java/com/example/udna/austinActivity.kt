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

class austinActivity : AppCompatActivity() {


    lateinit var adapter: NotesAdapter
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_austin)


        val newChar = findViewById<FloatingActionButton>(R.id.newNoteButton)
        val charListView = findViewById<ListView>(R.id.NoteList)
        val emptyList = findViewById<TextView>(R.id.NoteEmpty)

        newChar.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, Spell::class.java)
            startActivity(intent)
        })

        val charactersList = redrawNotesList(this,emptyList)

        adapter = NotesAdapter(this, android.R.layout.simple_list_item_1, charactersList)
        charListView.adapter = adapter
        charListView.setOnItemClickListener { parent, view, position, id ->
            val item = adapter.getView(position,null,parent)
            val intent = Intent(this, Spell::class.java)
            intent.putExtra("notes", charactersList.find{it.id == item.tag})
            intent.putExtra("position", position)
            startActivityForResult(intent, RESULT_OK)
        }
    }
    override fun onResume(){
        super.onResume()
        val emptyList = findViewById<TextView>(R.id.NoteEmpty)
        val charactersList = redrawNotesList(this,emptyList)
        adapter.clear()
        adapter.addAll(charactersList)
        adapter.notifyDataSetChanged()
    }

}

fun redrawNotesList(context: Context,emptyList:TextView) : MutableList<DndNote>{
    val directory = File(context.getExternalFilesDir(null), "notes")
    val charactersList = buildNotesList(directory)
    if(charactersList.isEmpty()){
        emptyList.text = "Do NOTE that you should add stuff in here"
    }else{
        emptyList.text = ""
    }
    return charactersList
}

fun buildNotesList(directory: File) : MutableList<DndNote>{
    val gson = Gson()
    val charactersList = mutableListOf<DndNote>()
    val characterFilesList = directory.listFiles()
    for(file in characterFilesList){
        val character = gson.fromJson(file.readText(), DndNote::class.java)
        charactersList.add(character)
    }
    return charactersList
}

class NotesAdapter(context: Context, resource: Int, objects:MutableList<DndNote>) : ArrayAdapter<DndNote>(context, resource, objects){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.note_list_layout,parent,false)
        }
        val currentItem = getItem(position)
        val nameText = view?.findViewById<TextView>(R.id.list_item_name)


        nameText?.text = currentItem?.name


        if (currentItem != null) {
            view?.tag = currentItem.id
        }

        return view!!
    }

}