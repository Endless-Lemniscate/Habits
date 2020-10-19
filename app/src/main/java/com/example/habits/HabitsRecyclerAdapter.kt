package com.example.habits

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.habits.models.Habit
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_habit_list_item.view.*


//class MyViewHolder (
//    override val containerView: View
//) : RecyclerView.ViewHolder(containerView), LayoutContainer {
//
//    fun bind(habit: Habit){
//        name.text = habit.name
//        description.text = habit.description
//        priority.text = habit.priority.toString()
//        type_of_habit.text = habit.type_of_habit
//        period.text = habit.period.toString()
//        color.text = habit.color.toString()
//
//
//        //containerView.setOnClickListener { View ->
//            //Toast.makeText(View.context, "Ты удалил карточку номер $adapterPosition", Toast.LENGTH_SHORT).show()
//            //this@HabitsRecyclerAdapter.removeAt(adapterPosition)
//        //}
//    }
//}

class HabitsRecyclerAdapter(private val items: MutableList<Habit>) :
    RecyclerView.Adapter<HabitsRecyclerAdapter.HabitsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): HabitsViewHolder {
        val inflater:LayoutInflater = LayoutInflater.from(parent.context)
        return HabitsViewHolder(inflater.inflate(R.layout.layout_habit_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: HabitsViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    class HabitsViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(habit: Habit) {
            containerView.apply {
                name.text = habit.name
                description.text = habit.description
                priority.text = habit.priority.toString()
                type_of_habit.text = habit.type_of_habit
                period.text = habit.period.toString()
                color.text = habit.color.toString()
            }
        }
    }

}




