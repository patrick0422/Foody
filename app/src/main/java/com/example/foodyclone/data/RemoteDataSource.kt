package com.example.foodyclone.data

import com.example.foodyclone.data.network.FoodRecipesApi
import com.example.foodyclone.models.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodRecipesApi: FoodRecipesApi
) {
    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe> = foodRecipesApi.getRecipe(queries)

    suspend fun searchRecipes(queries: Map<String, String>): Response<FoodRecipe> = foodRecipesApi.searchRecipes(queries)


}