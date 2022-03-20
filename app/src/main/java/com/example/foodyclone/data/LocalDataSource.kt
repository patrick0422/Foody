package com.example.foodyclone.data

import com.example.foodyclone.data.database.RecipesDao
import com.example.foodyclone.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDao: RecipesDao
) {

    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        recipesDao.insertRecipes(recipesEntity)
    }

    fun readRecipes(): Flow<List<RecipesEntity>> = recipesDao.readRecipes()
}