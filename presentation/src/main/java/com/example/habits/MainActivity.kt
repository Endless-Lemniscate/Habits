package com.example.habits

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.habits.ui.list.ListViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_header.view.*
import kotlinx.android.synthetic.main.fragment_about.view.*


lateinit var listViewModelFactory: ListViewModelFactory

@ExperimentalStdlibApi
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //init viewModelFactory for ListViewModel
        val appComponent = (application as HabitsApplication).applicationComponent
        val loadHabitUseCase = appComponent.getLoadHabitsUseCase()
        val deleteHabitUseCase = appComponent.getDeleteHabitUseCase()
        val accomplishHabitUseCase = appComponent.getAccomplishHabitUseCase()
        val syncHabitsWithRemoteUseCase = appComponent.getSyncHabitsWithRemoteUseCase()
        listViewModelFactory = ListViewModelFactory(loadHabitUseCase, deleteHabitUseCase, accomplishHabitUseCase, syncHabitsWithRemoteUseCase)

        //init navigation controller
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController: NavController = navHostFragment.navController
        NavigationUI.setupWithNavController(navigation_drawer, navController)

        //init navigation drawer header layout
        val navHeader = navigation_drawer.getHeaderView(0)

        val glide = appComponent.getGlide()
        val url = "https://thispersondoesnotexist.com/image"
        glide
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .override(80, 80)
            .centerCrop()
            .transform(CircleCrop())
            .placeholder(R.drawable.ic_avatar)
            .error(R.drawable.ic_avatar)
            .into(navHeader.avatar)
    }

}