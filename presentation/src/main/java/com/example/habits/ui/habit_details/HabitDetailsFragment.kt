package com.example.habits.ui.habit_details

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.domain.model.enums.HabitPeriod
import com.example.domain.model.enums.HabitPriority
import com.example.domain.usecases.InsertHabitUseCase
import com.example.domain.usecases.GetHabitByIdUseCase
import com.example.habits.HabitsApplication
import com.example.habits.R
import com.example.habits.databinding.FragmentHabitDetailsBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.fragment_habit_details.view.*
import kotlinx.coroutines.launch


lateinit var habitDetailsViewModel: HabitDetailsViewModel

class HabitDetailsFragment: Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appComp = (requireActivity().application as HabitsApplication).applicationComponent
        val getHabitsByIdUseCase = appComp.getGetHabitByIdUseCase()
        val insertHabitUseCase = appComp.getInsertHabitUseCase()
        val id = arguments?.getInt("habit_id")

        habitDetailsViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return modelClass.getConstructor(
                    GetHabitByIdUseCase::class.java,
                    InsertHabitUseCase::class.java,
                    Integer::class.java)
                    .newInstance(getHabitsByIdUseCase, insertHabitUseCase, id)
            }
        }).get(HabitDetailsViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val binding: FragmentHabitDetailsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_habit_details,
            container,
            false
        )

        //bind after data loaded
        habitDetailsViewModel.loaded.observe(viewLifecycleOwner, { loaded ->
            if (loaded) {
                binding.viewModel = habitDetailsViewModel
            }
        })

        val view = binding.root

        //if id presented change layout to edit mode
        if(arguments?.getInt("habit_id") != null) {
            view.submit_button.text = getString(R.string.save)
            view.header.text = getString(R.string.edit_habit)
        }

        //colorPicker display logic
        habitDetailsViewModel.colorPickerIsShowed.observe(viewLifecycleOwner, { showed ->
            val sheet =  BottomSheetBehavior.from(view.bottom_sheet)
            if(showed) {
                sheet.isHideable = true
                sheet.state =  BottomSheetBehavior.STATE_EXPANDED
            }
            else{
                sheet.isHideable = true
                sheet.state =  BottomSheetBehavior.STATE_HIDDEN
            }
        })

        //observe color for "color-showing" element
        habitDetailsViewModel.habit.observe(viewLifecycleOwner, {
            view.col.background = ColorDrawable(it.color)
        })

        //save to db and navigate to list by button click
        view.submit_button.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                val message = habitDetailsViewModel.insertHabitToDB()
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_habit_details_to_home_fragment)
            }
            //findNavController().navigate(R.id.action_habit_details_to_home_fragment)
        }

        //set "color-showing" element onclick to open bottomSheet colorPicker
        view.col.setOnClickListener {
            habitDetailsViewModel.showColorPicker()
        }

        //period dropdown init
        val periods = HabitPeriod.values().map { it.string }.toTypedArray()
        val a = ArrayAdapter(view.context, R.layout.spinner_item, periods)
        view.habit_period.setAdapter(a)

        //priority spinner init
        view.priority.minValue = 0
        view.priority.maxValue = 2
        val priorityNames = HabitPriority.values().map { it.string }.toTypedArray()
        view.priority.displayedValues = priorityNames

        return view
    }


}

