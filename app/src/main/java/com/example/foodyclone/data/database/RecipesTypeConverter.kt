package com.example.foodyclone.data.database

import androidx.room.TypeConverter
import com.example.foodyclone.models.FoodRecipe
import com.example.foodyclone.models.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipesTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun foodRecipeToString(foodRecipe: FoodRecipe): String = gson.toJson(foodRecipe)

    @TypeConverter
    fun stringToFoodRecipe(data: String): FoodRecipe =
        gson.fromJson(
            data,
            object : TypeToken<FoodRecipe>() {}.type
        )

    @TypeConverter
    fun resultToString(result: Result): String = gson.toJson(result)

    @TypeConverter
    fun stringToResult(data: String): Result = gson.fromJson(
        data,
        object : TypeToken<Result>() {}.type
    )
}