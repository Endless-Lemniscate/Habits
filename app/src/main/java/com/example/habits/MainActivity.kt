package com.example.habits


import android.os.Bundle

import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import androidx.viewpager2.widget.ViewPager2
import com.example.habits.models.Habit
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

var data: ArrayList<Habit> = ArrayList()


class MainActivity : AppCompatActivity(), Communicator, NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //list_view_pager_view.registerOnPageChangeCallback(doppelgangerPageChangeCallback)


//        val drawerToggle = ActionBarDrawerToggle(this,
//            navigationDrawerLayout,
//            R.string.open_drawer,
//            R.string.close_drawer)
//        navigationDrawerLayout.addDrawerListener(drawerToggle)

        navigation_drawer.setNavigationItemSelectedListener(this)

        if (savedInstanceState == null) {
            val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
            val viewPagerFragment = ViewPagerFragment()
            ft.replace(R.id.fragment_placeholder, viewPagerFragment)
            ft.commit()
        }
    }

    override fun passDataToHabit(bundle: Bundle, elem:View) {
        val habitFragment = HabitFragment()
        habitFragment.arguments = bundle

        val ft = this.supportFragmentManager.beginTransaction()
        //ft.addSharedElement(elem, "shared_element_container")
        ft.add(R.id.fragment_placeholder, habitFragment)
        ft.commit()
    }

    override fun returnHabitToList(habit: Habit, isNew: Boolean, index: Int, view: View) {
        val viewPagerFragment = ViewPagerFragment()
        val listFragment = ListFragment()
        val ft = supportFragmentManager.beginTransaction()

        //ft.addSharedElement(view, "shared_element_container")
        ft.replace(R.id.fragment_placeholder, viewPagerFragment)
        ft.commit()

        if (isNew) {
            data.add(index, habit)
            recyclerView.scrollToPosition(index)
            viewAdapter.notifyItemInserted(index)
        } else {
            data.removeAt(index)
            data.add(index, habit)
            recyclerView.scrollToPosition(index)
            viewAdapter.notifyItemInserted(index)
        }


//        listFragment.callback = object : ListFragmentCallBack {
//            override fun onFragmentViewResumed() {
//                if (isNew) {
//                    data.add(index, habit)
//                    recyclerView.scrollToPosition(index)
//                    viewAdapter.notifyItemInserted(index)
//                } else {
//                    data.removeAt(index)
//                    data.add(index, habit)
//                    recyclerView.scrollToPosition(index)
//                    viewAdapter.notifyItemInserted(index)
//                }
//                listFragment.callback = null
//            }
//        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        navigationDrawerLayout.closeDrawers()
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        val fragment = when (item.itemId){
            R.id.home_screen -> ViewPagerFragment()
            else -> AboutFragment()
        }
        ft.replace(R.id.fragment_placeholder, fragment)
        ft.commit()
        return true
    }


//    var doppelgangerPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
//        override fun onPageSelected(position: Int) {
//            Toast.makeText(this@MainActivity, "Selected position: ${position}",
//                Toast.LENGTH_SHORT).show()
//        }
//    }


}