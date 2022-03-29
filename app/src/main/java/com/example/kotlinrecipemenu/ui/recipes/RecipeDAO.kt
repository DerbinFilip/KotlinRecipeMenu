package com.example.kotlinrecipemenu.ui.recipes

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RecipeDao {

    @Query("SELECT * FROM recipe_table")
    fun getAllRecipes(): LiveData<List<Recipe>>

    @Query("delete from recipe_table")
    fun deleteAllRecipes()

    @Delete
    fun delete(recipe: Recipe)

    @Insert
    fun insert(recipe: Recipe)

    @Update
    fun update(recipe: Recipe)
}