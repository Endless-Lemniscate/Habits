package com.example.habits.ui.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Habit
import com.example.habits.R
import kotlinx.android.synthetic.main.list_item.view.*


class HabitsRecyclerAdapter(context: Context, private val cellClickListener: CellClickListener) :
    ListAdapter<Habit, HabitsRecyclerAdapter.HabitViewHolder>(DIFF_CALLBACK) {


    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Habit?> =
            object : DiffUtil.ItemCallback<Habit?>() {

                override fun areItemsTheSame(oldItem: Habit, newItem: Habit): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Habit, newItem: Habit): Boolean {
                    return oldItem == newItem
                }
            }
    }

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val itemView = inflater.inflate(R.layout.list_item, parent, false)
        return HabitViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    internal fun getHabitAt(position: Int) : Habit {
        return getItem(position)
    }

    inner class HabitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(habit: Habit) {
            itemView.apply {
                name.text = habit.name
                description.text = habit.description
                priority.text = habit.priority.name
                type_of_habit.text = habit.type.name
                frequency.text = habit.count.toString()
                period.text = habit.period.toString()
                color.text = habit.color.toString()
            }

            itemView.setOnClickListener{
                cellClickListener.onCellClickListener(habit.id)
            }

            itemView.button_done.setOnClickListener {
                cellClickListener.habitDoneClickListener(habit)
            }

        }

    }

}

interface CellClickListener {
    fun onCellClickListener(habit_id: Int)

    fun habitDoneClickListener(habit: Habit)
}








