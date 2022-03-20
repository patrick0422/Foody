package com.example.foodyclone.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.foodyclone.data.database.entities.RecipesEntity
import com.example.foodyclone.models.FoodRecipe
import com.example.foodyclone.util.NetworkResult

object RecipesBinding {

    @JvmStatic
    @BindingAdapter("readApiResponse", "readDatabase", requireAll = true)
    fun errorImageViewVisibility(
        view: ImageView,
        apiResponse: NetworkResult<FoodRecipe?>?,
        database: List<RecipesEntity>?
    ) {
        if (apiResponse is NetworkResult.Error && database.isNullOrEmpty()) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.INVISIBLE
        }
    }

    @JvmStatic
    @BindingAdapter("readApiResponse2", "readDatabase2", requireAll = true)
    fun errorTextViewVisibility(
        view: TextView,
        apiResponse: NetworkResult<FoodRecipe?>?,
        database: List<RecipesEntity>?
    ) {
        if (apiResponse is NetworkResult.Error && database.isNullOrEmpty()) {
            view.visibility = View.VISIBLE
            view.text = apiResponse.message
        } else {
            view.visibility = View.INVISIBLE
        }
    }
}