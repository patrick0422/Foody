package com.example.foodyclone.data.network

import com.example.foodyclone.models.FoodJoke
import com.example.foodyclone.models.FoodRecipe
import com.example.foodyclone.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface FoodRecipesApi {
    @GET("/recipes/complexSearch")
    suspend fun getRecipe(
        @QueryMap queries: Map<String, String>
    ): Response<FoodRecipe>

    @GET("/recipes/complexSearch")
    suspend fun searchRecipes(
        @QueryMap queries: Map<String, String>
    ): Response<FoodRecipe>

    @GET("/food/jokes/random")
    suspend fun getRandomFoodJoke(
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<FoodJoke>
}