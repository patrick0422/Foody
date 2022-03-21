package com.example.foodyclone.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.navArgs
import com.example.foodyclone.R
import com.example.foodyclone.adapters.PagerAdapter
import com.example.foodyclone.databinding.ActivityDetailsBinding
import com.example.foodyclone.ui.fragments.ingredients.IngredientsFragment
import com.example.foodyclone.ui.fragments.instructions.InstructionsFragment
import com.example.foodyclone.ui.fragments.overview.OverviewFragment
import com.example.foodyclone.util.Constants.Companion.BUNDLE_RECIPE_KEY
import com.google.android.material.tabs.TabLayoutMediator

class DetailsActivity : AppCompatActivity() {
    private val binding: ActivityDetailsBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_details) }
    private val args by navArgs<DetailsActivityArgs>()

    override fun onCreate(savedInstanceState: Bundle?): Unit = with(binding) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(getColor(R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val titles = arrayListOf(
            "OverView",
            "Ingredients",
            "Instructions"
        )

        val bundle = Bundle()
        bundle.putParcelable(BUNDLE_RECIPE_KEY, args.result)
        viewPager.adapter = PagerAdapter(
            this@DetailsActivity,
            bundle,
            listOf(
                OverviewFragment(),
                IngredientsFragment(),
                InstructionsFragment()
            )
        )

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }
}