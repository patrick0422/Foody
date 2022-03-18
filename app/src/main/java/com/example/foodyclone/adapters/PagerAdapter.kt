package com.example.foodyclone.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

//class PagerAdapter(
//    private val resultBundle: Bundle,
//    private val fragments: ArrayList<Fragment>,
//    private val title: ArrayList<String>,
//    fragmentManager: FragmentManager
//): FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
//
//    override fun getCount(): Int = fragments.size
//
//    override fun getItem(position: Int): Fragment {
//        fragments[position].arguments = resultBundle
//
//        return fragments[position]
//    }
//
//    override fun getPageTitle(position: Int): CharSequence? = title[position]
//}

class PagerAdapter(
    activity: FragmentActivity,
    private val fragments: ArrayList<Fragment>,
    private val resultBundle: Bundle
): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position].also {
            it.arguments = resultBundle
        }
    }

}