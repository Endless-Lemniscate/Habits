package com.example.habits

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.habits.models.Habit
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.list_item.view.*


class HabitsRecyclerAdapter(private val items: ArrayList<Habit>, private val activity: FragmentActivity?) :
    RecyclerView.Adapter<HabitsRecyclerAdapter.HabitsViewHolder>() {
    private lateinit var communicator: Communicator

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): HabitsViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val inflatedView = inflater.inflate(R.layout.list_item, parent, false)
        communicator = activity as Communicator
        return HabitsViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: HabitsViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size


    inner class HabitsViewHolder( override val containerView: View )
        : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(habit: Habit) {
            containerView.apply {
                name.text = habit.name
                description.text = habit.description
                priority.text = when (habit.priority) {1 -> "Высокий приоритет" 2 -> "Средний приоритет" else -> "Низкий приоритет" }
                type_of_habit.text = if (habit.habit_type) "Хорошая привычка" else "Плохая привычка"
                times.text = habit.frequency.toString()
                period.text = habit.period
                color.text = habit.color.toString()
            }

            containerView.setOnClickListener {
                containerView.card_view.transitionName = "shared_element_container"
                val hab = items[adapterPosition]

                val bundle = Bundle()
                bundle.apply {
                    putBoolean("isNew", false)
                    putInt("index", adapterPosition)
                    putString("name", hab.name)
                    putString("description", hab.description)
                    putInt("frequency", hab.frequency.toInt())
                    putString("period", hab.period)
                    putBoolean("habitType", hab.habit_type)
                    putInt("priority", hab.priority.toInt())
                    putInt("color", habit.color.toInt())
                }
                communicator.passDataToHabit(bundle, containerView.card_view)
            }

        }
    }
}




