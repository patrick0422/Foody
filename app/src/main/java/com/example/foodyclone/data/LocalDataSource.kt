package com.example.foodyclone.data

import com.example.foodyclone.data.database.RecipesDao
import com.example.foodyclone.data.database.entities.FavoritesEntity
import com.example.foodyclone.data.database.entities.FoodJokeEntity
import com.example.foodyclone.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDao: RecipesDao
) {

    suspend fun insertRecipes(recipesEntity: RecipesEntity) = recipesDao.insertRecipes(recipesEntity)

    fun readRecipes(): Flow<List<RecipesEntity>> = recipesDao.readRecipes()


    suspend fun insertFavoriteRecipes(favoritesEntity: FavoritesEntity) = recipesDao.insertFavoriteRecipes(favoritesEntity)

    fun readFavoriteRecipes(): Flow<List<FavoritesEntity>> = recipesDao.readFavorites()

    suspend fun deleteFavoriteRecipes(favoritesEntity: FavoritesEntity) = recipesDao.deleteFavoriteRecipes(favoritesEntity)

    suspend fun deleteAllFavoriteRecipes() = recipesDao.deleteAllFavoriteRecipes()

    fun readFoodJoke(): Flow<List<FoodJokeEntity>> = recipesDao.readFoodJoke()

    suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity) = recipesDao.insertFoodJoke(foodJokeEntity)
}