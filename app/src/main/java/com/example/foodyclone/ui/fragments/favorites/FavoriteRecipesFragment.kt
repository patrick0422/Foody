package com.example.foodyclone.ui.fragments.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodyclone.R
import com.example.foodyclone.adapters.FavoritesAdapter
import com.example.foodyclone.databinding.FragmentFavoriteRecipesBinding
import com.example.foodyclone.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteRecipesFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteRecipesBinding
    private val mainViewModel: MainViewModel by viewModels()
    private val mAdapter by lazy { FavoritesAdapter(requireActivity()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentFavoriteRecipesBinding>(inflater, R.layout.fragment_favorite_recipes, container, false).also {
            it.lifecycleOwner = this
            it.mainViewModel = mainViewModel
            it.adapter = mAdapter
        }

        setUpRecyclerView()

        mainViewModel.readFavoriteRecipes.observe(viewLifecycleOwner) {
            mAdapter.setData(it)
            binding.executePendingBindings()
        }

        return binding.root
    }

    private fun setUpRecyclerView() {
        binding.favoritesRecyclerView.adapter = mAdapter
    }
}