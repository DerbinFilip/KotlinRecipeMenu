package com.example.kotlinrecipemenu.ui.recipes

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.kotlinrecipemenu.ui.utils.subscribeOnBackground

class RecipeRepository(application: Application) {
    private var recipeDao: RecipeDao
    private var allRecipes: LiveData<List<Recipe>>
    private val database = RecipeDatabase.getInstance(application)

    init {
        recipeDao = database.recipeDao()
        allRecipes = recipeDao.getAllRecipes()
    }

    fun insert(recipe: Recipe) {
        subscribeOnBackground {
            recipeDao.insert(recipe)
        }
    }

    fun update(recipe: Recipe) {
        subscribeOnBackground {
            recipeDao.update(recipe)
        }
    }

    fun delete(recipe: Recipe) {
        subscribeOnBackground {
            recipeDao.delete(recipe)
        }
    }

    fun deleteAllRecipes() {
        subscribeOnBackground {
            recipeDao.deleteAllRecipes()
        }
    }

    fun getAllRecipes(): LiveData<List<Recipe>> {
        return allRecipes
    }
}