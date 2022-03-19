package com.example.foodyclone.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.foodyclone.R
import com.example.foodyclone.databinding.FragmentIngredientsBinding
import com.example.foodyclone.databinding.IngredientsRowLayoutBinding
import com.example.foodyclone.models.ExtendedIngredient
import com.example.foodyclone.util.Constants.Companion.BASE_IMAGE_URL
import com.example.foodyclone.util.RecipesDiffUtil

class IngredientsAdapter: RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>() {
    private var ingredientsList = emptyList<ExtendedIngredient>()

    class IngredientsViewHolder(private val binding: IngredientsRowLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): IngredientsViewHolder = IngredientsViewHolder(
                IngredientsRowLayoutBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
        fun bind(ingredient: ExtendedIngredient) = with(binding) {
            ingredientImageView.load(BASE_IMAGE_URL + ingredient.image) {
                crossfade(true)
                error(R.drawable.ic_sad)
            }
            ingredientName.text = ingredient.name
            ingredientAmount.text = ingredient.amount.toString()
            ingredientUnit.text = ingredient.unit
            ingredientConsistency.text = ingredient.consistency
            ingredientOriginal.text = ingredient.original
        }
    }

    fun setData(newData: List<ExtendedIngredient>) {
        val result = DiffUtil.calculateDiff(RecipesDiffUtil(ingredientsList, newData))
        ingredientsList = newData

        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder = IngredientsViewHolder.from(parent)
    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) = holder.bind(ingredientsList[position])
    override fun getItemCount(): Int = ingredientsList.size
}