package com.example.foodyclone.bindingadapters

import android.view.View
import androidx.databinding.BindingAdapter
import com.example.foodyclone.data.database.entities.FoodJokeEntity
import com.example.foodyclone.models.FoodJoke
import com.example.foodyclone.util.NetworkResult

object FoodJokeBinding {
    @JvmStatic
    @BindingAdapter("readApiResponse", "readDatabase", requireAll = false)
    fun setCardAndProgressVisibility(
        view: View,
        readApiResponse: NetworkResult<FoodJoke>?,
        readDatabase: FoodJokeEntity?
    ) {

    }
}