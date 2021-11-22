package com.example.foodyclone.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodyclone.data.DataStoreRepository
import com.example.foodyclone.util.Constants.Companion.API_KEY
import com.example.foodyclone.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.foodyclone.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.example.foodyclone.util.Constants.Companion.DEFAULT_RECIPES_NUMBER
import com.example.foodyclone.util.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.example.foodyclone.util.Constants.Companion.QUERY_API_KEY
import com.example.foodyclone.util.Constants.Companion.QUERY_DIET
import com.example.foodyclone.util.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.example.foodyclone.util.Constants.Companion.QUERY_NUMBER
import com.example.foodyclone.util.Constants.Companion.QUERY_TYPE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class RecipesViewModel @Inject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {

    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE

    val readMealAndDietType = dataStoreRepository.readMealAndDietType

    fun saveMealAndDietType(
        selectedMealType: String,
        selectedMealTypeId: Int,
        selectedDietType: String,
        selectedDietTypeId: Int
    ) = viewModelScope.launch(Dispatchers.IO) {
        dataStoreRepository.saveMealAndDietType(
            selectedMealType,
            selectedMealTypeId,
            selectedDietType,
            selectedDietTypeId
        )
    }

    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        // Flow는 collect를 통해 값을 받아옴
        viewModelScope.launch(Dispatchers.IO) {
            readMealAndDietType.collect { value ->
                mealType = value.selectedMealType
                dietType = value.selectedDietType
            }
        }

        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = mealType
        queries[QUERY_DIET] = dietType
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }
}