package com.example.kotlinrecipemenu.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlinrecipemenu.R
import android.app.Activity
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.home.*

const val EXTRA_ID = " com.example.kotlinrecipemenu.ui.EXTRA_ID"
const val EXTRA_TITLE = " com.example.kotlinrecipemenu.ui.EXTRA_TITLE"
const val EXTRA_INGREDIENTS = " com.example.kotlinrecipemenu.ui.EXTRA_DESCRIPTION"

class AddEditRecipe : AppCompatActivity() {

    private lateinit var mode: Mode
    private var recipeId: Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        recipeId = intent.getIntExtra(EXTRA_ID, -1)
        mode = if (recipeId == -1) Mode.AddRecipe
        else Mode.EditRecipe

        when (mode) {
            Mode.AddRecipe -> title = "Add Recipe"
            Mode.EditRecipe -> {
                title = "Edit Recipe"
                et_title.setText(intent.getStringExtra(EXTRA_TITLE))
                et_ingredients.setText(intent.getStringExtra(EXTRA_INGREDIENTS))
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.add_recipe_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_recipe -> {
                saveRecipe()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveRecipe() {
        val title = et_title.text.toString()
        val ingredients = et_ingredients.text.toString()
        if (title.isEmpty() || ingredients.isEmpty()) {
            Toast.makeText(this, "please insert title and ingredients", Toast.LENGTH_SHORT).show()
            return
        }
        val data = Intent()
        if (recipeId != -1)
            data.putExtra(EXTRA_ID, recipeId)
        data.putExtra(EXTRA_TITLE, title)
        data.putExtra(EXTRA_INGREDIENTS, ingredients)
        setResult(Activity.RESULT_OK, data)
        finish()
    }

    private sealed class Mode {
        object AddRecipe : Mode()
        object EditRecipe : Mode()
    }
}