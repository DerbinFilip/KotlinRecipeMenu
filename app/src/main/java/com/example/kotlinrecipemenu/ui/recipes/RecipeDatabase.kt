package com.example.kotlinrecipemenu.ui.recipes

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.kotlinrecipemenu.ui.utils.subscribeOnBackground
import com.example.kotlinrecipemenu.ui.recipes.RecipeDatabase as DB

@Database(entities = [Recipe::class], version = 3)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    companion object {
        private var instance: DB? = null

        @Synchronized
        fun getInstance(ctx: Context): DB {
            if (instance == null)
                instance =
                    Room.databaseBuilder(ctx.applicationContext, DB::class.java, "recipe_database")
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build()
            return instance!!
        }

        private val roomCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                populateDatabase(instance!!)
            }
        }

        private fun populateDatabase(db: DB) {
            val recipeDao = db.recipeDao()
            subscribeOnBackground {
                recipeDao.insert(Recipe("Pomidorowa", "Lorem ipsum Lorem ipsum Lorem ipsumLorem ipsum Lorem ipsum "))
                recipeDao.insert(Recipe("Ogórkowa", "Lorem ipsumLorem ipsumLorem ipsumLorem ipsum"))
                recipeDao.insert(Recipe("Kalafiorowa", "Lorem ipsumLorem ipsumLorem ipsumLorem ipsumLorem ipsumLorem ipsumLorem ipsumLorem ipsumLorem ipsum"))
            }
        }
    }
}