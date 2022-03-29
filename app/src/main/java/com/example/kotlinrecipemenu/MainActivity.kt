package com.example.kotlinrecipemenu

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinrecipemenu.databinding.ActivityMainBinding
import com.example.kotlinrecipemenu.ui.*
import com.example.kotlinrecipemenu.ui.recipes.Recipe
import com.example.kotlinrecipemenu.ui.recipes.RecipeAdapter
import com.example.kotlinrecipemenu.ui.recipes.RecipeViewModel
import kotlinx.android.synthetic.main.activity_main.*

const val ADD_RECIPE_REQUEST = 1
const val EDIT_RECIPE_REQUEST = 2

class MainActivity : AppCompatActivity() {

    private lateinit var vm: RecipeViewModel
    private lateinit var adapter: RecipeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpRecyclerView()
        setUpListeners()
        vm = ViewModelProviders.of(this)[RecipeViewModel::class.java]
        vm.getAllRecipes().observe(this, Observer {
            Log.i("Recipes observed", "$it")
            adapter.submitList(it)
        })
    }

    private fun setUpListeners() {
        button_add_recipe.setOnClickListener {
            val intent = Intent(this, AddEditRecipe::class.java)
            startActivityForResult(intent, ADD_RECIPE_REQUEST)
        }
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, LEFT or RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val recipe = adapter.getRecipeAt(viewHolder.adapterPosition)
                vm.delete(recipe)
            }
        }).attachToRecyclerView(recycler_view)
    }

    private fun setUpRecyclerView() {
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
        adapter = RecipeAdapter { clickedRecipe ->
            val intent = Intent(this, AddEditRecipe::class.java)
            intent.putExtra(EXTRA_ID, clickedRecipe.id)
            intent.putExtra(EXTRA_TITLE, clickedRecipe.recipeTitle)
            intent.putExtra(EXTRA_INGREDIENTS, clickedRecipe.recipeIngredients)
            startActivityForResult(intent, EDIT_RECIPE_REQUEST)
        }
        recycler_view.adapter = adapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null && requestCode == ADD_RECIPE_REQUEST && resultCode == Activity.RESULT_OK) {
            val title: String = data.getStringExtra(EXTRA_TITLE).toString()
            val ingredients: String = data.getStringExtra(EXTRA_INGREDIENTS).toString()
            vm.insert(Recipe(title, ingredients))
            Toast.makeText(this, "Recipe inserted!", Toast.LENGTH_SHORT).show()
        } else if (data != null && requestCode == EDIT_RECIPE_REQUEST && resultCode == Activity.RESULT_OK) {
            val id = data.getIntExtra(EXTRA_ID, -1)
            if (id == -1) {
                Toast.makeText(this, "Recipe couldn't be updated!", Toast.LENGTH_SHORT).show()
                return
            }
            val title: String = data.getStringExtra(EXTRA_TITLE).toString()
            val ingredients: String = data.getStringExtra(EXTRA_INGREDIENTS).toString()
            vm.update(Recipe(title, ingredients))
            Toast.makeText(this, "Recipe updated", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Recipe not saved!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_all_recipes -> {
                vm.deleteAllRecipes()
                Toast.makeText(this, "All recipes deleted!", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}