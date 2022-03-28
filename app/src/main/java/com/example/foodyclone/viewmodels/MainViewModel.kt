package com.example.foodyclone.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.example.foodyclone.data.Repository
import com.example.foodyclone.data.database.entities.FavoritesEntity
import com.example.foodyclone.data.database.entities.RecipesEntity
import com.example.foodyclone.models.FoodJoke
import com.example.foodyclone.models.FoodRecipe
import com.example.foodyclone.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    /** ROOM */

    val readRecipes: LiveData<List<RecipesEntity>> = repository.local.readRecipes().asLiveData()

    private fun insertRecipes(recipesEntity: RecipesEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.local.insertRecipes(recipesEntity)
    }

    val readFavoriteRecipes: LiveData<List<FavoritesEntity>> =
        repository.local.readFavoriteRecipes().asLiveData()

    fun insertFavoriteRecipes(favoritesEntity: FavoritesEntity) = viewModelScope.launch {
        repository.local.insertFavoriteRecipes(favoritesEntity)
    }

    fun deleteFavoriteRecipes(favoritesEntity: FavoritesEntity) = viewModelScope.launch {
        repository.local.deleteFavoriteRecipes(favoritesEntity)
    }

    fun deleteAllFavoriteRecipes() = viewModelScope.launch {
        repository.local.deleteAllFavoriteRecipes()
    }



    /** RETROFIT */

    val recipesResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()

    val searchResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()

    val foodJokeResponse: MutableLiveData<NetworkResult<FoodJoke>> = MutableLiveData()

    fun getRecipes(queries: Map<String, String>) = viewModelScope.launch {
        getRecipesSafeCall(queries)
    }

    fun searchRecipes(searchQuery: Map<String, String>) = viewModelScope.launch {
        searchQuerySafeCall(searchQuery)
    }

    fun getFoodJoke() = viewModelScope.launch {
        getFoodJokeSafeCall()
    }

    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {
        recipesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getRecipes(queries)
                recipesResponse.value = handleFoodRecipesResponse(response)

                recipesResponse.value!!.data.also {
                    if (it != null) {
                        offlineCacheRecipes(it)
                    }
                }
            } catch (e: Exception) {
                recipesResponse.value = NetworkResult.Error("Recipe not found.")
            }
        } else {
            recipesResponse.value = NetworkResult.Error("No internet connection.")
        }
    }

    private suspend fun searchQuerySafeCall(searchQuery: Map<String, String>) {
        searchResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.searchRecipes(searchQuery)
                searchResponse.value = handleFoodRecipesResponse(response)

            } catch (e: Exception) {
                searchResponse.value = NetworkResult.Error("Recipe not found.")
            }
        } else {
            searchResponse.value = NetworkResult.Error("No internet connection.")
        }
    }

    private suspend fun getFoodJokeSafeCall() {
        foodJokeResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getRandomFoodJoke()
                foodJokeResponse.value = handleFoodJokeResponse(response)

            } catch (e: Exception) {
                foodJokeResponse.value = NetworkResult.Error("Recipe not found.")
            }
        } else {
            foodJokeResponse.value = NetworkResult.Error("No internet connection.")
        }
    }

    private fun handleFoodJokeResponse(response: Response<FoodJoke>): NetworkResult<FoodJoke> {
        when {
            response.message().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API key limited.")
            }
            response.isSuccessful -> {
                return NetworkResult.Success(response.body()!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    private fun offlineCacheRecipes(foodRecipe: FoodRecipe) {
        val recipesEntity = RecipesEntity(foodRecipe)
        insertRecipes(recipesEntity)
    }

    private fun handleFoodRecipesResponse(response: Response<FoodRecipe>): NetworkResult<FoodRecipe> {
        when {
            response.message().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API key limited.")
            }
            response.body()!!.results.isNullOrEmpty() -> {
                return NetworkResult.Error("Recipe not found.")
            }
            response.isSuccessful -> {
                return NetworkResult.Success(response.body()!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}