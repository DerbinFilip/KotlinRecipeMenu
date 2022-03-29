package com.example.kotlinrecipemenu.ui.recipes

import androidx.room.*
import com.example.kotlinrecipemenu.ui.utils.subscribeOnBackground

@Entity(tableName = "recipe_table")
data class Recipe(
    val recipeTitle: String,
    val recipeIngredients: String,
    @PrimaryKey(autoGenerate = false) val id: Int? = null,
)