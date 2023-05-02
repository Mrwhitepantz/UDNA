package com.example.udna
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import java.io.File
import java.io.Serializable

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley


class Spell : AppCompatActivity() {
    lateinit var charName: EditText
    //lateinit var level: EditText
    lateinit var Desc: EditText

    lateinit var existingCharacter: DndNote
    var listViewPosition = -1
    //private  lateinit var binding: ActivityMainBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding = ActivityMainBinding.inflate(layoutInflater)
        //setContentView()
        setContentView(R.layout.activity_spell)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        charName = findViewById(R.id.spell_name)
        //level = findViewById(R.id.SpellLevel)
        Desc= findViewById(R.id.DescSpell)


        val character = if (intent.hasExtra("notes")){
            intent.getSerializableExtra("notes") as DndNote
        } else{
            findViewById<FloatingActionButton>(R.id.deleteSpellButton).visibility = View.GONE
            newSpell(this)
        }
        if(intent.hasExtra("notes")){
            existingCharacter = character
        }

        listViewPosition = intent.getIntExtra("position", -1)

        displaySpellInfo(character)
    }



    fun fetchInfo(view: View) {
        /*
            NOTE: you need to set the INTERNET permission in the manifest, and
            add Volley to your module gradle file as a dependency
         */
        // get the text fields and image field
        val title = findViewById<TextView>(R.id.spell_name)
        val errorT = findViewById<TextView>(R.id.noData)

        val desc = findViewById<TextView>(R.id.DescSpell)

        // set all values to the base value
        var input = title.text


        // say if its featching the data
        // use the url and if date is specified then go to that date on apod. if date is null then
        // it goes to today. also uses https and not http requests
        var lookChar=""







        //title.text=input
        val url ="https://www.dnd5eapi.co/api/spells/"+input.toString().toLowerCase()

        val queue = Volley.newRequestQueue(this)

        val jsonRequest = JsonObjectRequest(Request.Method.GET, // what sort of request
            url, // url as a string
            null, // json data to throw at the server
            Response.Listener { response ->
                desc.text = ""

                desc.text = desc.text.toString() + "Description: " + response.getString("desc").substring(2,response.getString("desc").length-2)+"\n"+"\n"


                //desc.movementMethod = ScrollingMovementMethod()
                desc.text =desc.text.toString() + "higher_level: " + response.getString("higher_level").substring(1,response.getString("higher_level").length-1)+"\n"+"\n"
                desc.text =desc.text.toString() + "range: " + response.getString("range").substring(0,response.getString("range").length)+"\n"+"\n"
                desc.text =desc.text.toString() + "components: " + response.getString("components").substring(2,response.getString("components").length-2)+"\n"+"\n"
                if (response.has("material")) {
                    desc.text =desc.text.toString() + "material: " + response.getString("material").substring(0,response.getString("material").length)+"\n"+"\n"
                }

                desc.text =desc.text.toString() + "ritual: " + response.getString("ritual").substring(0,response.getString("ritual").length)+"\n"+"\n"
                desc.text =desc.text.toString() + "concentration: " + response.getString("concentration").substring(0,response.getString("concentration").length)+"\n"+"\n"
                desc.text =desc.text.toString() + "casting_time: " + response.getString("casting_time").substring(0,response.getString("casting_time").length)+"\n"+"\n"
                desc.text =desc.text.toString() + "level: " + response.getString("level").substring(0,response.getString("level").length)+"\n"+"\n"

                desc.movementMethod = ScrollingMovementMethod()


            },
            Response.ErrorListener { error ->
                // what to do if things fail
                desc.text = "That didn't work: ${error.localizedMessage}  "+input
            })

        queue.add(jsonRequest)
    }



    fun fetchInfoitem(view: View) {
        /*
            NOTE: you need to set the INTERNET permission in the manifest, and
            add Volley to your module gradle file as a dependency
         */
        // get the text fields and image field
        val title = findViewById<TextView>(R.id.spell_name)
        val errorT = findViewById<TextView>(R.id.noData)

        val desc = findViewById<TextView>(R.id.DescSpell)

        // set all values to the base value
        var input = title.text


        // say if its featching the data
        // use the url and if date is specified then go to that date on apod. if date is null then
        // it goes to today. also uses https and not http requests
        var lookChar=""







        //title.text=input
        val url ="https://www.dnd5eapi.co/api/magic-items/"+input.toString().toLowerCase()

        val queue = Volley.newRequestQueue(this)

        val jsonRequest = JsonObjectRequest(Request.Method.GET, // what sort of request
            url, // url as a string
            null, // json data to throw at the server
            Response.Listener { response ->
                desc.text = ""

                desc.text = desc.text.toString() + response.getString("desc").substring(2,response.getString("desc").length-2)+"\n"+"\n"


                //desc.movementMethod = ScrollingMovementMethod()
                /*desc.text =desc.text.toString() + "higher_level: " + response.getString("higher_level").substring(1,response.getString("higher_level").length-1)+"\n"+"\n"
                desc.text =desc.text.toString() + "range: " + response.getString("range").substring(0,response.getString("range").length)+"\n"+"\n"
                desc.text =desc.text.toString() + "components: " + response.getString("components").substring(2,response.getString("components").length-2)+"\n"+"\n"
                if (response.has("material")) {
                    desc.text =desc.text.toString() + "material: " + response.getString("material").substring(0,response.getString("material").length)+"\n"+"\n"
                }

                desc.text =desc.text.toString() + "ritual: " + response.getString("ritual").substring(0,response.getString("ritual").length)+"\n"+"\n"
                desc.text =desc.text.toString() + "concentration: " + response.getString("concentration").substring(0,response.getString("concentration").length)+"\n"+"\n"
                desc.text =desc.text.toString() + "casting_time: " + response.getString("casting_time").substring(0,response.getString("casting_time").length)+"\n"+"\n"
                desc.text =desc.text.toString() + "level: " + response.getString("level").substring(0,response.getString("level").length)+"\n"+"\n"
*/
                desc.movementMethod = ScrollingMovementMethod()


            },
            Response.ErrorListener { error ->
                // what to do if things fail
                desc.text = "That didn't work: ${error.localizedMessage}  "+input
            })

        queue.add(jsonRequest)
    }



    private fun newSpell(context: Context): DndNote {
        val character = DndNote(
            0,
            "Note",
            "Note information"
        )
        character.id = DndNote.getNextID()
        return character
    }

    private fun displaySpellInfo(character: DndNote) {
        charName.setText(character.name)

        Desc.setText(character.Desc)

    }

    fun saveSpellInfo(view: View){
        val charName = charName.text.toString()
        //val charLevel = level.text.toString().toInt()
        val charRace = Desc.text.toString()



        val gson = Gson()
        val directory = File(this.getExternalFilesDir(null), "notes")
        val characterFilesList = directory.listFiles()

        var character: DndNote = if(!::existingCharacter.isInitialized) {
            newSpell(this)
        }else{
            existingCharacter
        }
        character.name = charName

        character.Desc = charRace

        val characterToSave = characterFilesList.find { it.name == character.id.toString() }
        if(characterToSave != null){
            characterToSave.writeText(gson.toJson(character))
        } else{
            val newCharacterFile = File(directory, character.id.toString())
            newCharacterFile.writeText(gson.toJson(character))
        }
        File(this.getExternalFilesDir(null),"notes_ids").writeText(character.id.toString())

        val resultIntent = Intent()
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    fun deleteNote(view: View) {
        val directory = File(this.getExternalFilesDir(null), "notes")
        val characterFilesList = directory.listFiles()

        characterFilesList.find { it.name == existingCharacter.id.toString() }?.delete()

        val resultIntent = Intent()
        resultIntent.putExtra("position", listViewPosition)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
}

class DndNote(
    var id: Int,
    var name: String,
    var Desc: String
) : Serializable {
    companion object{
        var lastID = 0
        lateinit var spellContext: Context
        fun init(context: Context){
            spellContext = context
            val directory = File(spellContext.getExternalFilesDir(null), "notes")
            val idFile = File(directory, "notes_ids")
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

class spellAbility(
    val name: String,
    val score: Int,
    val modifier: Int
) : Serializable