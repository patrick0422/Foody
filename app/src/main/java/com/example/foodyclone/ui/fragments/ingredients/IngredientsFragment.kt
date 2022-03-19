package com.example.foodyclone.ui.fragments.ingredients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.foodyclone.R
import com.example.foodyclone.adapters.IngredientsAdapter
import com.example.foodyclone.databinding.FragmentIngredientsBinding
import com.example.foodyclone.models.Result
import com.example.foodyclone.util.Constants

class IngredientsFragment : Fragment() {
    private lateinit var binding: FragmentIngredientsBinding
    private val ingredientsAdapter by lazy { IngredientsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ingredients, container, false)

        binding.ingredientsRecyclerview.adapter = ingredientsAdapter

        val args = arguments
        val result: Result? = args?.getParcelable(Constants.BUNDLE_RECIPE_KEY)

        result?.extendedIngredients?.let { ingredientsAdapter.setData(it) }

        return binding.root
    }
}