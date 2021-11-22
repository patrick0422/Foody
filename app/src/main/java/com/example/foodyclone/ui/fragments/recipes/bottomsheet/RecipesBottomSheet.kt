package com.example.foodyclone.ui.fragments.recipes.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.foodyclone.R
import com.example.foodyclone.databinding.RecipesBottomSheetBinding
import com.example.foodyclone.util.Constants
import com.example.foodyclone.viewmodels.RecipesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.lang.Exception


class RecipesBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: RecipesBottomSheetBinding
    private lateinit var recipesViewModel: RecipesViewModel

    private var mealTypeChip = Constants.DEFAULT_MEAL_TYPE
    private var mealTypeChipId = 0
    private var dietTypeChip = Constants.DEFAULT_DIET_TYPE
    private var dietTypeChipId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.recipes_bottom_sheet, container, false)

        recipesViewModel.readMealAndDietType.asLiveData().observe(viewLifecycleOwner, { value ->
            mealTypeChip = value.selectedMealType
            dietTypeChip = value.selectedDietType
            updateChip(value.selectedMealTypeId, binding.mealTypeChipGroup)
            updateChip(value.selectedDietTypeId, binding.dietTypeChipGroup)
        })

        binding.mealTypeChipGroup.setOnCheckedChangeListener { _, selectedChipId ->
            val selectedChip = binding.root.findViewById<Chip>(selectedChipId)
            val selectedMealType = selectedChip.text.toString().lowercase()

            mealTypeChip = selectedMealType
            mealTypeChipId = selectedChipId
        }
        binding.dietTypeChipGroup.setOnCheckedChangeListener { _, selectedChipId ->
            val selectedChip = binding.root.findViewById<Chip>(selectedChipId)
            val selectedDietType = selectedChip.text.toString().lowercase()

            dietTypeChip = selectedDietType
            dietTypeChipId = selectedChipId
        }

        binding.applyButton.setOnClickListener {
            recipesViewModel.saveMealAndDietType(
                mealTypeChip,
                mealTypeChipId,
                dietTypeChip,
                dietTypeChipId
            )

            val action = RecipesBottomSheetDirections.actionRecipesBottomSheetToRecipesFragment(true)
            findNavController().navigate(action)
        }

        return binding.root
    }

    private fun updateChip(chipId: Int, chipGroup: ChipGroup) {
        if (chipId != 0) {
            try {
                chipGroup.findViewById<Chip>(chipId).isChecked = true
            } catch (e: Exception) {
                Log.d("TAG", "updateChip: ${e.stackTraceToString()}")
            }
        }
    }
}