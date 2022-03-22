package com.example.foodyclone.bindingadapters

import android.text.Html
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.example.foodyclone.R
import com.example.foodyclone.models.Result
import com.example.foodyclone.ui.fragments.favorites.FavoriteRecipesFragmentDirections
import com.example.foodyclone.ui.fragments.recipes.RecipesFragmentDirections
import com.example.foodyclone.util.Constants.Companion.TAG
import org.jsoup.Jsoup

object RecipesRowBinding {

    @JvmStatic
    @BindingAdapter("onFavoriteRecipeClickListener")
    fun onFavoriteRecipeClickListener(recipeRowLayout: ConstraintLayout, result: Result) = recipeRowLayout.setOnClickListener {
        try {
            val action = FavoriteRecipesFragmentDirections.actionFavoriteRecipesFragmentToDetailsActivity(result)
            recipeRowLayout.findNavController().navigate(action)
        } catch (e: Exception) {
            Log.d(TAG, "onRecipeClickListener: ${e.stackTraceToString()}")
        }
    }

    @JvmStatic
    @BindingAdapter("onRecipeClickListener")
    fun onRecipeClickListener(recipeRowLayout: ConstraintLayout, result: Result) = recipeRowLayout.setOnClickListener {
        try {
            val action = RecipesFragmentDirections.actionRecipesFragmentToDetailsActivity(result)
            recipeRowLayout.findNavController().navigate(action)
        } catch (e: Exception) {
            Log.d(TAG, "onRecipeClickListener: ${e.stackTraceToString()}")
        }
    }

    @JvmStatic
    @BindingAdapter("setNumberOfLikes")
    fun setNumberOfLikes(textView: TextView, likes: Int) {
        textView.text = likes.toString()
    }

    @JvmStatic
    @BindingAdapter("setNumberOfMinutes")
    fun setNumberOfMinutes(textView: TextView, minutes: Int) {
        textView.text = minutes.toString()
    }

    @JvmStatic
    @BindingAdapter("applyVeganColor")
    fun applyVeganColor(view: View, vegan: Boolean) {
        if (vegan) {
            when (view) {
                is TextView -> view.setTextColor(
                    ContextCompat.getColor(
                        view.context,
                        R.color.green
                    )
                )
                is ImageView -> view.setColorFilter(
                    ContextCompat.getColor(
                        view.context,
                        R.color.green
                    )
                )
            }
        }
    }

    @JvmStatic
    @BindingAdapter("loadImageFromUrl")
    fun loadImageFromUrl(imageView: ImageView, imageUrl: String) {
        imageView.load(imageUrl) {
            crossfade(600)
            error(R.drawable.ic_food)
        }
    }

    @JvmStatic
    @BindingAdapter("parseHtml")
    fun parseHtml(textView: TextView, description: String?) {
        if (description != null) {
//            textView.text = Jsoup.parse(description).text()
            textView.text = Html.fromHtml(description, Html.FROM_HTML_MODE_LEGACY)
        }
    }
}