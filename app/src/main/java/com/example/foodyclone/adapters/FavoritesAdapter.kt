package com.example.foodyclone.adapters

import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foodyclone.R
import com.example.foodyclone.data.database.entities.FavoritesEntity
import com.example.foodyclone.databinding.FavoriteRecipesRowLayoutBinding
import com.example.foodyclone.ui.fragments.favorites.FavoriteRecipesFragmentDirections
import com.example.foodyclone.util.RecipesDiffUtil

class FavoritesAdapter(
    private val requireActivity: FragmentActivity
) : RecyclerView.Adapter<FavoriteHolder>() {
    private var recipes = emptyList<FavoritesEntity>()
    private var selectedRecipes = arrayListOf<FavoritesEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FavoriteHolder.from(parent)

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) = with(holder.binding) {

        favoritesEntity = favoritesEntity

        favoriteRecipesRowLayout.setOnClickListener {
            val action = FavoriteRecipesFragmentDirections.actionFavoriteRecipesFragmentToDetailsActivity(favoritesEntity.result)
            it.findNavController().navigate(action)
        }

        favoriteRecipesRowLayout.setOnLongClickListener {
            requireActivity.startActionMode(object : ActionMode.Callback {
                override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                    mode?.menuInflater?.inflate(R.menu.favorites_contextual_menu, menu)
                    applyStatusBarColor(requireActivity, R.color.contextualStatusBarColor)
                    return true
                }
                override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {

                    return true
                }
                override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {

                    return true
                }
                override fun onDestroyActionMode(mode: ActionMode?) {
                    applyStatusBarColor(requireActivity, R.color.statusBarColor)
                }
            })
            true
        }

        executePendingBindings()
    }

    private fun applyStatusBarColor(requireActivity: FragmentActivity, color: Int) {
        requireActivity.window.statusBarColor = ContextCompat.getColor(requireActivity, color)
    }

    override fun getItemCount(): Int = recipes.size

    private fun applySelection(holder: FavoriteHolder, selectedRecipe: FavoritesEntity) {
        if (selectedRecipes.contains(selectedRecipe)) {
            selectedRecipes.remove(selectedRecipe)
            changeRecipeStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)
        }
        else {
            selectedRecipes.add(selectedRecipe)
            changeRecipeStyle(holder, R.color.cardBackgroundLightColor, R.color.colorPrimary)
        }
    }

    private fun changeRecipeStyle(
        holder: FavoriteHolder,
        backGroundColor: Int,
        strokeColor: Int
    ) {
        holder.binding.apply {
            favoriteRecipesRowLayout.setBackgroundColor(ContextCompat.getColor(requireActivity, backGroundColor))
            favoriteRowCardView.strokeColor = strokeColor
        }
    }

    fun setData(newData: List<FavoritesEntity>) {
        val recipesDiffUtil = RecipesDiffUtil(recipes, newData)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        recipes = newData

        diffUtilResult.dispatchUpdatesTo(this)
    }
}

class FavoriteHolder(val binding: FavoriteRecipesRowLayoutBinding): RecyclerView.ViewHolder(binding.root) {
    private var multiSelection = false

    companion object {
        fun from(parent: ViewGroup): FavoriteHolder = FavoriteHolder(
            FavoriteRecipesRowLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }
}