package com.example.udna

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import java.io.File
import java.io.Serializable

class map_content : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    lateinit var mapName: EditText
    var playercount : Int = 0
    private val mapArray = Array(9){IntArray(9)}
    lateinit var existingMap: chosenMap
    var listViewPosition = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_content)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val togglemenu = findViewById<Button>(R.id.colorbutton)
        mapName = findViewById(R.id.map_name)

        val map = if (intent.hasExtra("map")){
            intent.getSerializableExtra("map") as chosenMap
        } else{
            findViewById<FloatingActionButton>(R.id.deletemapButton).visibility = View.GONE
            newMap(this)
        }
        if(intent.hasExtra("map")){
            existingMap = map
        }

        listViewPosition = intent.getIntExtra("position", -1)

        displayMapInfo(map)

    }
    fun colorpickerButton (view:View){
        val calendar = findViewById<ListView>(R.id.color_Picker)
        if(calendar.visibility == View.VISIBLE){
            calendar.visibility = View.GONE
        } else {
            calendar.visibility = View.VISIBLE
        }

    }

    private fun newMap(context: Context): chosenMap {
        val map = chosenMap(
            0,
            "Empty Map",
            0,
            Array(9){IntArray(9)},

        )
        map.id = chosenMap.getNextID()
        return map
    }
    private fun displayMapInfo(map: chosenMap) {
        mapName.setText(map.name)
    }
    fun saveMapInfo(view: View){
        val mapName = mapName.text.toString()
        val gson = Gson()
        val directory = File(this.getExternalFilesDir(null), "maps")
        val mapFilesList = directory.listFiles()

        var map: chosenMap = if(!::existingMap.isInitialized) {
            newMap(this)
        }else{
            existingMap
        }
        map.name = mapName
        map.playercount = playercount
        map.mapgrid = mapArray


        val mapToSave = mapFilesList?.find { it.name == map.id.toString() }
        if(mapToSave != null){
            mapToSave.writeText(gson.toJson(map))
        } else{
            val newMapFile = File(directory, map.id.toString())
            newMapFile.writeText(gson.toJson(map))
        }
        val resultIntent = Intent()
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
    fun deleteMap(view: View) {
        val directory = File(this.getExternalFilesDir(null), "maps")
        val mapFilesList = directory.listFiles()

        mapFilesList.find { it.name == existingMap.id.toString() }?.delete()

        val resultIntent = Intent()
        resultIntent.putExtra("position", listViewPosition)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
}
class chosenMap(
    var id: Int,
    var name: String,
    var playercount: Int,
    var mapgrid: Array<IntArray>,
) : Serializable {
    companion object{
        var lastID = 0
        @SuppressLint("StaticFieldLeak")
        lateinit var charContext: Context
        fun init(context: Context){
            charContext = context
            val directory = File(charContext.getExternalFilesDir(null), "maps")
            val idFile = File(directory, "map_idfile")
            if(!idFile.exists()){
                idFile.writeText(0.toString())
            }
            lastID = idFile.readText().toInt()
        }
        fun getNextID():Int{
            return lastID++
        }
    }
}