package com.example.foodyclone.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foodyclone.data.database.entities.FavoritesEntity
import com.example.foodyclone.databinding.FavoriteRecipesRowLayoutBinding
import com.example.foodyclone.models.FoodRecipe
import com.example.foodyclone.models.Result
import com.example.foodyclone.util.RecipesDiffUtil

class FavoritesAdapter : RecyclerView.Adapter<FavoritesAdapter.FavoriteHolder>() {
    private var recipes = emptyList<FavoritesEntity>()

    class FavoriteHolder(private val binding: FavoriteRecipesRowLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(favoritesEntity: FavoritesEntity) {
            binding.favoritesEntity = favoritesEntity
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): FavoriteHolder = FavoriteHolder(
                FavoriteRecipesRowLayoutBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        return FavoriteHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) = holder.bind(recipes[position])

    override fun getItemCount(): Int = recipes.size

    fun setData(newData: List<FavoritesEntity>) {
        val recipesDiffUtil = RecipesDiffUtil(recipes, newData)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        recipes = newData

        diffUtilResult.dispatchUpdatesTo(this)
    }
}