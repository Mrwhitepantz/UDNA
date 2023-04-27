package com.example.udna

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import com.google.gson.Gson
import java.io.File

class CharacterInfo : AppCompatActivity() {
    var charName = findViewById<EditText>(R.id.char_name)
    var level = findViewById<EditText>(R.id.level_text)
    var race = findViewById<EditText>(R.id.race_text)
    var className = findViewById<EditText>(R.id.class_text)
    var scoreStr = findViewById<EditText>(R.id.score_str)
    var scoreDex = findViewById<EditText>(R.id.score_dex)
    var scoreCon = findViewById<EditText>(R.id.score_con)
    var scoreInt = findViewById<EditText>(R.id.score_int)
    var scoreWis = findViewById<EditText>(R.id.score_wis)
    var scoreCha = findViewById<EditText>(R.id.score_cha)
    var bonusStr = findViewById<TextView>(R.id.bonus_str)
    var bonusDex = findViewById<TextView>(R.id.bonus_dex)
    var bonusCon = findViewById<TextView>(R.id.bonus_con)
    var bonusInt = findViewById<TextView>(R.id.bonus_int)
    var bonusWis = findViewById<TextView>(R.id.bonus_wis)
    var bonusCha = findViewById<TextView>(R.id.bonus_cha)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_info)

        val gson = Gson()
        val charactersJson = File("res/values/characters.json").readText()
        val charactersList = gson.fromJson(charactersJson, Array<DndCharacter>::class.java)

        val character = if (intent.hasExtra("name")){
            loadCharacter(charactersList, intent.getStringExtra("name"))
        } else{
            newCharacter()
        }

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
        level.setText(character.level)
        race.setText(character.race)
        className.setText(character.className)

        scoreStr.setText(character.abilities[0].score)
        scoreDex.setText(character.abilities[1].score)
        scoreCon.setText(character.abilities[2].score)
        scoreInt.setText(character.abilities[3].score)
        scoreWis.setText(character.abilities[4].score)
        scoreCha.setText(character.abilities[5].score)

        bonusStr.setText(character.abilities[0].modifier)
        bonusDex.setText(character.abilities[1].modifier)
        bonusCon.setText(character.abilities[2].modifier)
        bonusInt.setText(character.abilities[3].modifier)
        bonusWis.setText(character.abilities[4].modifier)
        bonusCha.setText(character.abilities[5].modifier)
    }

    private fun saveCharacterInfo(){
        val charName = charName.text.toString()
        val charLevel = level.text.toString().toInt()
        val charRace = race.text.toString()
        val charClass = className.text.toString()
        val abilities = listOf<Ability>(Ability("Strength", scoreStr.text.toString().toInt(), bonusStr.text.toString().toInt()),
            Ability("Dexterity", scoreDex.text.toString().toInt(), bonusDex.text.toString().toInt()),
            Ability("Constitution",  scoreCon.text.toString().toInt(), bonusCon.text.toString().toInt()),
            Ability("Intelligence",  scoreInt.text.toString().toInt(), bonusInt.text.toString().toInt()),
            Ability("Wisdom",  scoreWis.text.toString().toInt(), bonusWis.text.toString().toInt()),
            Ability("Charisma",  scoreCha.text.toString().toInt(), bonusCha.text.toString().toInt()))

        val gson = Gson()
        val file = File("res/values/characters.json")
        val charactersJson = file.readText()
        var charactersList = gson.fromJson(charactersJson, Array<DndCharacter>::class.java)
        var character = charactersList.find{it.name == charName}?: DndCharacter(charName,charLevel,charRace,charClass,abilities,
            emptyList())

        character.level = charLevel
        character.race = charRace
        character.className = charClass
        character.abilities = abilities
        character.inventory = emptyList()

        val newCharacterList = charactersList.toMutableList()
        newCharacterList.add(character)
        charactersList = newCharacterList.toTypedArray()

        val saveCharacterListJson = gson.toJson(charactersList)
        file.writeText(saveCharacterListJson)
    }
}

class DndCharacter(
    var name: String,
    var level: Int,
    var race: String,
    var className: String,
    var abilities: List<Ability>,
    var inventory: List<String>
)

class Ability(
    val name: String,
    val score: Int,
    val modifier: Int
)