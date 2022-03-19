package com.example.foodyclone.ui.fragments.instructions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import com.example.foodyclone.R
import com.example.foodyclone.databinding.FragmentInstructionsBinding
import com.example.foodyclone.models.Result
import com.example.foodyclone.util.Constants

class InstructionsFragment : Fragment() {
    private lateinit var binding: FragmentInstructionsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_instructions, container, false)

        val args = arguments
        val result: Result? = args?.getParcelable(Constants.BUNDLE_RECIPE_KEY)

        binding.instructionsWebView.webViewClient = object : WebViewClient() {

        }
        binding.instructionsWebView.loadUrl(result?.sourceUrl ?: "")

        return binding.root
    }
}