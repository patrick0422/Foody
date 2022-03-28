package com.example.foodyclone.ui.fragments.favorites

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodyclone.R
import com.example.foodyclone.adapters.FavoritesAdapter
import com.example.foodyclone.databinding.FragmentFavoriteRecipesBinding
import com.example.foodyclone.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteRecipesFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteRecipesBinding
    private val mainViewModel: MainViewModel by viewModels()
    private val mAdapter by lazy { FavoritesAdapter(requireActivity(), mainViewModel) }

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
        setHasOptionsMenu(true)

        mainViewModel.readFavoriteRecipes.observe(viewLifecycleOwner) {
            mAdapter.setData(it)
            binding.executePendingBindings()
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        mAdapter.clearContextualActionMode()
    }

    private fun setUpRecyclerView() {
        binding.favoritesRecyclerView.adapter = mAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.favorite_recipes_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.deleteAll_favorite_recipes_menu) {
            mainViewModel.deleteAllFavoriteRecipes()
            Snackbar.make(binding.root, "All Recipes Removed", Snackbar.LENGTH_SHORT).setAction("Okay"){}.show()

            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}