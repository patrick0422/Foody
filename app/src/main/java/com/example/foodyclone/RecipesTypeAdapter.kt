package com.example.foodyclone

import androidx.room.TypeConverter
import com.example.foodyclone.models.FoodRecipe
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipesTypeAdapter {
    val gson = Gson()

    @TypeConverter
    fun foodRecipeToString(foodRecipe: FoodRecipe): String = gson.toJson(foodRecipe)

    @TypeConverter
    fun StringToFoodRecipe(data: String): FoodRecipe {
        val listType = object : TypeToken<FoodRecipe>() {}.type
        return gson.fromJson(data, listType)
    }
}