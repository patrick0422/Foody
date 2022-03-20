package com.example.foodyclone.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodyclone.models.FoodRecipe
import com.example.foodyclone.util.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
    var foodRecipe: FoodRecipe
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}