package com.example.foodyclone.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.navArgs
import com.example.foodyclone.R
import com.example.foodyclone.adapters.PagerAdapter
import com.example.foodyclone.data.database.entities.FavoritesEntity
import com.example.foodyclone.databinding.ActivityDetailsBinding
import com.example.foodyclone.ui.fragments.ingredients.IngredientsFragment
import com.example.foodyclone.ui.fragments.instructions.InstructionsFragment
import com.example.foodyclone.ui.fragments.overview.OverviewFragment
import com.example.foodyclone.util.Constants.Companion.BUNDLE_RECIPE_KEY
import com.example.foodyclone.util.Constants.Companion.TAG
import com.example.foodyclone.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {
    private val binding: ActivityDetailsBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_details) }
    private val mainViewModel: MainViewModel by viewModels()
    private val args by navArgs<DetailsActivityArgs>()

    private var isRecipeSaved = false
    private var savedRecipeId = 0

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

    private fun checkFavoritesStatus(menuItem: MenuItem) {
        mainViewModel.readFavoriteRecipes.observe(this) { response ->
            response.filter {
                it.result.recipeId == args.result.recipeId
            }.let {
                Log.d(TAG, "checkFavoritesStatus: ${it.size}")
                
                if (it.isNotEmpty()) {
                    savedRecipeId = it[0].id
                    isRecipeSaved = true
                    setMenuItemTint(menuItem, R.color.yellow)
                }
            }
        }
    }

    private fun saveToFavorites(item: MenuItem) {
        mainViewModel.insertFavoriteRecipes(
            FavoritesEntity(0, args.result)
        )
        setMenuItemTint(item, R.color.yellow)
        makeSnackbar(message = "Saved")

        isRecipeSaved = true
    }

    private fun removeFromFavorites(item: MenuItem) {
        mainViewModel.deleteFavoriteRecipes(
            FavoritesEntity(savedRecipeId, args.result)
        )

        setMenuItemTint(item, R.color.white)
        makeSnackbar("Recipe Removed")
        isRecipeSaved = false
    }

    private fun makeSnackbar(message: String) {
        Snackbar.make(binding.detailsLayout, message, Snackbar.LENGTH_SHORT)
            .setAction("Okay") {}
            .show()
    }

    private fun setMenuItemTint(menuItem: MenuItem, colorId: Int) {
        menuItem.icon.setTint(resources.getColor(colorId, null))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)

        val menuItem = menu?.findItem(R.id.menu_save_to_favorites)
        menuItem?.let {
            checkFavoritesStatus(it)
            setMenuItemTint(it, R.color.white)
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        } else if (item.itemId == R.id.menu_save_to_favorites) {
            if (isRecipeSaved)
                removeFromFavorites(item)
            else
                saveToFavorites(item)
        }

        return super.onOptionsItemSelected(item)
    }
}