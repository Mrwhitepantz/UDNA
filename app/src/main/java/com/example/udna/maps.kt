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
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File
import com.google.gson.Gson

class maps : AppCompatActivity() {

    lateinit var adapter: MapAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val button = findViewById<FloatingActionButton>(R.id.newmapbutton)
        button.setOnClickListener {
            val intent = Intent(this, map_content::class.java)
            startActivity(intent)
        }
        val mapsList = redrawList(this)
        val mapListView = findViewById<ListView>(R.id.maps_list)
        adapter = MapAdapter(this, android.R.layout.simple_list_item_1, mapsList)
        mapListView.adapter = adapter
        mapListView.setOnItemClickListener { parent, view, position, id ->
            val name = findViewById<TextView>(R.id.list_item_name)
            val intent = Intent(this, CharacterInfo::class.java)
            intent.putExtra("map", mapsList.find { it.name == name.text })
            intent.putExtra("position", position)
            startActivityForResult(intent, RESULT_OK)


        }
    }

    override fun onResume() {
        super.onResume()
        adapter.clear()
        adapter.notifyDataSetChanged()
    }

    fun redrawList(context: Context): MutableList<chosenMap> {
        val directory = File(context.getExternalFilesDir(null), "maps")
        val mapsList = buildMapsList(directory)
        return mapsList
    }

    fun buildMapsList(directory: File): MutableList<chosenMap> {
        val gson = Gson()
        val mapsList = mutableListOf<chosenMap>()
        val mapFilesList = directory.listFiles()
        if (mapFilesList != null){
            for (file in mapFilesList) {
                val map = gson.fromJson(file.readText(), chosenMap::class.java)
                mapsList.add(map)
            }
        }
        return mapsList
    }
}
    class MapAdapter(context: Context, resource: Int, objects:MutableList<chosenMap>) : ArrayAdapter<chosenMap>(context, resource, objects){
        @SuppressLint("SetTextI18n")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var view = convertView
            if(view == null){
                view = LayoutInflater.from(context).inflate(R.layout.maps_list_layout,parent,false)
            }
            val currentItem = getItem(position)
            val nameText = view?.findViewById<TextView>(R.id.list_item_name)
            val playerText = view?.findViewById<TextView>(R.id.list_item_player_count)

            nameText?.text = currentItem?.name
            playerText?.text =  "${currentItem?.playercount} Players"

            return view!!
        }
    }