package com.example.foodyclone.data.database

import androidx.room.*
import com.example.foodyclone.data.database.entities.FavoritesEntity
import com.example.foodyclone.data.database.entities.RecipesEntity
import com.example.foodyclone.models.Result
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipesEntity: RecipesEntity)

    @Query("SELECT * FROM recipes_table")
    fun readRecipes(): Flow<List<RecipesEntity>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteRecipes(favoritesEntity: FavoritesEntity)

    @Query("SELECT * FROM favorite_recipes_table ORDER BY id ASC")
    fun readFavorites(): Flow<List<FavoritesEntity>>

    @Delete
    suspend fun deleteFavoriteRecipes(favoritesEntity: FavoritesEntity)

    @Query("DELETE FROM favorite_recipes_table")
    suspend fun deleteAllFavoriteRecipes()
}