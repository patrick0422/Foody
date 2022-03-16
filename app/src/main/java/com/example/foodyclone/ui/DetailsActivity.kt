package com.example.foodyclone.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.foodyclone.R
import com.example.foodyclone.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {
    private val binding: ActivityDetailsBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_details) }

    override fun onCreate(savedInstanceState: Bundle?): Unit = with(binding) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(getColor(R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }
}