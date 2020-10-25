package com.example.habits

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.habits.models.Habit
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.android.synthetic.main.fragment_habit.*
import kotlinx.android.synthetic.main.fragment_habit.view.*
import kotlinx.android.synthetic.main.fragment_habit.view.submit_button

private const val ARG_ISNEW = "isNew"
private const val ARG_INDEX = "index"
private const val ARG_NAME = "name"
private const val ARG_DESCRIPTION = "description"
private const val ARG_FREQUENCY = "frequency"
private const val ARG_PERIOD = "period"
private const val ARG_HABIT_TYPE = "habitType"
private const val ARG_PRIORITY = "priority"
private const val ARG_COLOR = "color"


class HabitFragment : Fragment() {
    private var isNew: Boolean = true
    private var habitIndex: Int = -1
    private var habitName: String? = null
    private var habitDescription: String? = null
    private var habitFrequency: Number? = 1
    private var habitPeriod: String? = null
    private var habitType: Boolean = true
    private var habitPriority: Number? = null
    private var habitColor: Number? = 5

    private lateinit var communicator: Communicator
    var callback: HabitFragmentCallBack? = null

    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
        callback = activity as HabitFragmentCallBack
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().setDuration(500L)

        if (arguments?.getInt(ARG_INDEX) != -1) {
            arguments?.let {
                isNew = it.getBoolean(ARG_ISNEW)
                habitIndex = it.getInt(ARG_INDEX)
                habitName = it.getString(ARG_NAME)
                habitDescription = it.getString(ARG_DESCRIPTION)
                habitFrequency = it.getInt(ARG_FREQUENCY)
                habitPeriod = it.getString(ARG_PERIOD)
                habitType = it.getBoolean(ARG_HABIT_TYPE)
                habitPriority = it.getInt(ARG_PRIORITY)
                habitColor = it.getInt(ARG_COLOR)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_habit, container, false)

        communicator = activity as Communicator

        view.apply {
            habit_name.setText(habitName)
            habit_description.setText(habitDescription)
            habit_frequency.setText(habitFrequency.toString())
            habit_period.setText(habitPeriod)
            habit_type.check( if (habitType) R.id.good else R.id.bad )
            habit_priority.setText(habitPriority.toString())
            habit_color.setText(habitColor.toString())
        }

        view.submit_button.setOnClickListener {
            view.transitionName = "shared_element_container1"
            val habit = Habit(
                habit_name.text.toString(),
                habit_description.text.toString(),
                habit_frequency.text.toString().toInt(),
                habit_period.text.toString(),
                when (habit_type.checkedRadioButtonId) { R.id.good -> true else -> false },
                when(habit_priority.text.toString()){"Высокий" -> 1 "Средний" -> 2 else -> 3 },
                habit_color.text.toString().toInt()
            )

            communicator.returnHabitToList(habit, isNew, habitIndex, view)
        }



        val ad1 = ArrayAdapter(
            view.habit_period.context,
            R.layout.spinner_item,
            arrayOf("Час", "6 часов", "День", "3 дня", "Неделя", "Месяц", "2 месяца")
        )
        view.habit_period.apply{
            setAdapter(ad1)
            setText("День", false)
            setRawInputType(InputType.TYPE_NULL)
        }

        val ad2 = ArrayAdapter(
            view.context,
            R.layout.spinner_item,
            arrayOf("Высокий", "Средний", "Низкий")
        )
        view.habit_priority.apply {
            setAdapter(ad2)
            setText("Высокий", false)
            setRawInputType(InputType.TYPE_NULL)
        }



        return view
    }


    override fun onResume() {
        super.onResume()
        callback?.onFragmentViewResumed()
    }

    companion object {
        @JvmStatic
        fun newInstance(habitIndex: Int, habitName: String) =
            HabitFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_INDEX, habitIndex)
                    //putString(ARG_NAME, habitName)
                }
            }
    }
}

interface HabitFragmentCallBack {
    fun onFragmentViewResumed()
}
