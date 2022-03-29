package com.example.kotlinrecipemenu.ui.recipes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class RecipeViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = RecipeRepository(app)
    private val allRecipes = repository.getAllRecipes()

    fun insert(recipe: Recipe) {
        repository.insert(recipe)
    }

    fun update(recipe: Recipe) {
        repository.update(recipe)
    }

    fun delete(recipe: Recipe) {
        repository.delete(recipe)
    }

    fun deleteAllRecipes() {
        repository.deleteAllRecipes()
    }

    fun getAllRecipes(): LiveData<List<Recipe>> {
        return allRecipes
    }
}