package com.example.foodyclone.ui.fragments.foodjoke

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.foodyclone.R
import com.example.foodyclone.data.database.entities.FoodJokeEntity
import com.example.foodyclone.databinding.FragmentFoodJokeBinding
import com.example.foodyclone.util.NetworkResult
import com.example.foodyclone.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodJokeFragment : Fragment() {
    private lateinit var binding: FragmentFoodJokeBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_food_joke, container, false)


        binding.mainViewModel = mainViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        mainViewModel.getFoodJoke()
        mainViewModel.foodJokeResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding.foodJokeTextView.text = response.data!!.text
                }
                is NetworkResult.Error -> {
                    loadDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {

                }
            }
        }

        return binding.root
    }

    private fun loadDataFromCache() {
        mainViewModel.readFoodJoke.observe(viewLifecycleOwner) { database ->
            if (!database.isNullOrEmpty()) {
                binding.foodJokeTextView.text = database[0].foodJoke.text
            }
        }
    }
}