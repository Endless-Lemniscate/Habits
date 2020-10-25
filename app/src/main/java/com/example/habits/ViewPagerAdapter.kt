package com.example.habits

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter


class ViewPagerAdapter(activity: AppCompatActivity, val itemsCount: Int) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return itemsCount
    }

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> ListFragment.newInstance(false, 0, "All")
        1 -> ListFragment.newInstance(false, 0, "good")
        else -> ListFragment.newInstance(false, 0, "bad")
    }
}