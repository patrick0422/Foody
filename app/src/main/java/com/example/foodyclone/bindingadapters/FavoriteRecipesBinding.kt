package com.example.foodyclone.bindingadapters

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.foodyclone.adapters.FavoritesAdapter
import com.example.foodyclone.data.database.entities.FavoritesEntity

object FavoriteRecipesBinding {
    @JvmStatic
    @BindingAdapter("viewVisibility", "setData", requireAll = false)
    fun setDataAndViewVisibility(view: View, favoritesEntity: List<FavoritesEntity>?, adapter: FavoritesAdapter?) {
        if (favoritesEntity.isNullOrEmpty()) {
            when(view) {
                is RecyclerView -> {
                    view.visibility = View.INVISIBLE
                }
                else -> {
                    view.visibility = View.VISIBLE
                }
            }
        } else {
            when(view) {
                is RecyclerView -> {
                    view.visibility = View.VISIBLE
                    adapter?.setData(favoritesEntity)
                }
                else -> {
                    view.visibility = View.INVISIBLE
                }
            }
        }

    }
}