package com.example.habits


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.habits.ui.list.ListViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

lateinit var listViewModelFactory: ListViewModelFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //init viewModelFactory for ListViewModel
        val appComponent = (application as HabitsApplication).applicationComponent
        val loadHabitUseCase = appComponent.getLoadHabitsUseCase()
        val deleteHabitUseCase = appComponent.getDeleteHabitUseCase()
        val accomplishHabitUseCase = appComponent.getAccomplishHabitUseCase()
        listViewModelFactory = ListViewModelFactory(loadHabitUseCase, deleteHabitUseCase, accomplishHabitUseCase)

        //init navigation controller
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController: NavController = navHostFragment.navController
        NavigationUI.setupWithNavController(navigation_drawer, navController)
    }

}