package com.example.kotlinrecipemenu.ui.recipes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.example.kotlinrecipemenu.R

class RecipeAdapter(private val onItemClickListener: (Recipe) -> Unit) :
    ListAdapter<Recipe, RecipeAdapter.RecipeHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recipe_item, parent,
            false
        )
        return RecipeHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecipeHolder, position: Int) {
        with(getItem(position)) {
            holder.tvTitle.text = recipeTitle
            holder.tvIngredients.text = recipeIngredients
        }
    }

    fun getRecipeAt(position: Int) = getItem(position)

    inner class RecipeHolder(iv: View) : RecyclerView.ViewHolder(iv) {
        val tvTitle: TextView = itemView.findViewById(R.id.text_view_title)
        val tvIngredients: TextView = itemView.findViewById(R.id.text_view_ingredients)

        init {
            itemView.setOnClickListener {
                if (adapterPosition != NO_POSITION)
                    onItemClickListener(getItem(adapterPosition))
            }
        }
    }
}

private val diffCallback = object : DiffUtil.ItemCallback<Recipe>() {
    override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe) =
        oldItem.recipeTitle == newItem.recipeTitle
                && oldItem.recipeIngredients == newItem.recipeIngredients

    override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe) = oldItem.id == newItem.id
}