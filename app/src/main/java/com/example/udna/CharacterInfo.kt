package com.example.udna

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import java.io.File

class CharacterInfo : AppCompatActivity() {
    lateinit var charName: EditText
    lateinit var level: EditText
    lateinit var race: EditText
    lateinit var className: EditText
    lateinit var scoreStr: EditText
    lateinit var scoreDex: EditText
    lateinit var scoreCon: EditText
    lateinit var scoreInt: EditText
    lateinit var scoreWis: EditText
    lateinit var scoreCha: EditText
    lateinit var bonusStr: TextView
    lateinit var bonusDex: TextView
    lateinit var bonusCon: TextView
    lateinit var bonusInt: TextView
    lateinit var bonusWis: TextView
    lateinit var bonusCha: TextView
    var listViewPosition = -1

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_info)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        charName = findViewById(R.id.char_name)
        level = findViewById(R.id.level_text)
        race = findViewById(R.id.race_text)
        className = findViewById(R.id.class_text)
        scoreStr = findViewById(R.id.score_str)
        scoreDex = findViewById(R.id.score_dex)
        scoreCon = findViewById(R.id.score_con)
        scoreInt = findViewById(R.id.score_int)
        scoreWis = findViewById(R.id.score_wis)
        scoreCha = findViewById(R.id.score_cha)
        bonusStr = findViewById(R.id.bonus_str)
        bonusDex = findViewById(R.id.bonus_dex)
        bonusCon = findViewById(R.id.bonus_con)
        bonusInt = findViewById(R.id.bonus_int)
        bonusWis = findViewById(R.id.bonus_wis)
        bonusCha = findViewById(R.id.bonus_cha)

        val gson = Gson()
        val charactersJson = File(this.getExternalFilesDir(null), "characters.json").readText()
        val charactersList = gson.fromJson(charactersJson, Array<DndCharacter>::class.java)

        if(intent.hasExtra("newChar")){
            findViewById<FloatingActionButton>(R.id.deleteCharButton).visibility = View.GONE
        }
        val character = if (intent.hasExtra("name")){
            loadCharacter(charactersList, intent.getStringExtra("name"))
        } else{
            newCharacter()
        }

        listViewPosition = intent.getIntExtra("position", -1)

        displayCharacterInfo(character)
    }

    private fun newCharacter(): DndCharacter {
        return DndCharacter(
            "default",
            1,
            "Human",
            "Fighter",
            listOf(Ability("Strength", 10, 0),
                Ability("Dexterity", 10, 0),
                Ability("Constitution", 10, 0),
                Ability("Intelligence", 10, 0),
                Ability("Wisdom", 10, 0),
                Ability("Charisma", 10, 0)),
            emptyList()
        )
    }

    private fun loadCharacter(charactersList: Array<DndCharacter>, name: String?): DndCharacter {
        return charactersList.find {it.name == name}?: newCharacter()
    }

    private fun displayCharacterInfo(character: DndCharacter) {
        charName.setText(character.name)
        level.setText(character.level.toString())
        race.setText(character.race)
        className.setText(character.className)

        scoreStr.setText(character.abilities[0].score.toString())
        scoreDex.setText(character.abilities[1].score.toString())
        scoreCon.setText(character.abilities[2].score.toString())
        scoreInt.setText(character.abilities[3].score.toString())
        scoreWis.setText(character.abilities[4].score.toString())
        scoreCha.setText(character.abilities[5].score.toString())

        bonusStr.setText(character.abilities[0].modifier.toString())
        bonusDex.setText(character.abilities[1].modifier.toString())
        bonusCon.setText(character.abilities[2].modifier.toString())
        bonusInt.setText(character.abilities[3].modifier.toString())
        bonusWis.setText(character.abilities[4].modifier.toString())
        bonusCha.setText(character.abilities[5].modifier.toString())
    }

    fun saveCharacterInfo(){
        val charName = charName.text.toString()
        val charLevel = level.text.toString().toInt()
        val charRace = race.text.toString()
        val charClass = className.text.toString()
        val strength = scoreStr.text.toString().toInt()
        val dexterity = scoreDex.text.toString().toInt()
        val constitution = scoreCon.text.toString().toInt()
        val intelligence = scoreInt.text.toString().toInt()
        val wisdom = scoreWis.text.toString().toInt()
        val charisma = scoreCha.text.toString().toInt()
        val determineBonus = { score: Int -> ( score - 10 ) / 2 }

        val abilities = listOf(Ability("Strength", strength, determineBonus(strength)),
            Ability("Dexterity", dexterity,determineBonus(dexterity)),
            Ability("Constitution", constitution, determineBonus(constitution) ),
            Ability("Intelligence", intelligence, determineBonus(intelligence)),
            Ability("Wisdom", wisdom, determineBonus(wisdom)),
            Ability("Charisma", charisma, determineBonus(charisma))
        )

        val gson = Gson()
        val file = File(this.getExternalFilesDir(null), "characters.json")
        val charactersJson = file.readText()
        var charactersList = gson.fromJson(charactersJson, Array<DndCharacter>::class.java)
        var character = charactersList.find{it.name == charName}
        if(character == null) {
            character = DndCharacter(charName,charLevel,charRace,charClass,abilities,
            emptyList())
            val newCharacterList = charactersList.toMutableList()
            newCharacterList.add(character)
            charactersList = newCharacterList.toTypedArray()
        } else {
            character.level = charLevel
            character.race = charRace
            character.className = charClass
            character.abilities = abilities
            character.inventory = emptyList()
        }

        val saveCharacterListJson = gson.toJson(charactersList)
        file.writeText(saveCharacterListJson)

        displayCharacterInfo(character)
    }

    fun deleteCharacter(){
        val charName = charName.text.toString()

        val gson = Gson()
        val file = File(this.getExternalFilesDir(null), "characters.json")
        val charactersJson = file.readText()
        var charactersList = gson.fromJson(charactersJson, Array<DndCharacter>::class.java)
        val character = charactersList.find{it.name == charName}
        val newCharacterList = charactersList.toMutableList()
        newCharacterList.remove(character)
        charactersList = newCharacterList.toTypedArray()
        val deleteCharacterFromListJson = gson.toJson(charactersList)
        file.writeText(deleteCharacterFromListJson)

        finish()
    }
}

class DndCharacter(
    var name: String,
    var level: Int,
    var race: String,
    var className: String,
    var abilities: List<Ability>,
    var inventory: List<String>
){
//    override fun toString(): String{
//        return name
//    }
}

class Ability(
    val name: String,
    val score: Int,
    val modifier: Int
){}