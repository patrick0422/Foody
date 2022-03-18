package com.example.foodyclone.ui.fragments.overview

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import coil.load
import com.example.foodyclone.R
import com.example.foodyclone.databinding.FragmentOverviewBinding
import com.example.foodyclone.models.Result
import com.example.foodyclone.util.Constants.Companion.BUNDLE_RECIPE_KEY


class OverviewFragment : Fragment() {
    private lateinit var binding: FragmentOverviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_overview, container, false)

        val args = arguments
        val result: Result? = args?.getParcelable(BUNDLE_RECIPE_KEY)

        with(binding) {
            result?.run {
                imageView.load(image)
                textLikes.text = aggregateLikes.toString()
                textTime.text = readyInMinutes.toString()
                textTitle.text = title
                textSummary.text = Html.fromHtml(summary, Html.FROM_HTML_MODE_LEGACY)

                if (vegetarian) {
                    imageVegetarian.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                    textVegetarian.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                }
                if (vegan) {
                    imageVegan.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                    textVegan.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                }
                if (glutenFree) {
                    imageGlutenFree.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                    textGlutenFree.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                }
                if (dairyFree) {
                    imageDairyFree.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                    textDairyFree.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                }
                if (veryHealthy) {
                    imageHealthy.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                    textHealthy.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                }
                if (cheap) {
                    imageCheap.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                    textCheap.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                }
            }
        }

        return binding.root
    }
}