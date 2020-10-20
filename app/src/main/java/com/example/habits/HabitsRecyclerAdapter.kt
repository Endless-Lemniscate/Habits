package com.example.habits


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.habits.models.Habit
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_edit.*
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
        val inflatedView = inflater.inflate(R.layout.layout_habit_list_item, parent, false)
        return HabitsViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: HabitsViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class HabitsViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(habit: Habit) {
            containerView.apply {
                name.text = habit.name
                description.text = habit.description
                priority.text = when (habit.priority) {
                    1 -> "Высокий приоритет"
                    2 -> "Средний приоритет"
                    3 -> "Низкий приоритет"
                    else -> "Без приоритета"
                }

                type_of_habit.text = when (habit.type_of_habit){
                    "good" -> "Хорошая привычка"
                    "bad" -> "Плохая привычка"
                    else -> ""
                }
                times.text = habit.times.toString()
                period.text = habit.period
                color.text = habit.color.toString()
            }




            containerView.setOnClickListener {
                Toast.makeText(it.context, "Кликнули \"${items[position].name}\"", Toast.LENGTH_SHORT).show()
                val hab = items[adapterPosition]
                val intent = Intent(it.context, EditActivity::class.java)

                intent.apply {
                    putExtra("index", adapterPosition)
                    putExtra("name", hab.name)
                    putExtra("description", hab.description)
                    putExtra("priority", hab.priority)
                    putExtra("type_of_habit", hab.type_of_habit)
                    putExtra("times", hab.times.toString())
                    putExtra("period", hab.period)
                }
                items.removeAt(adapterPosition)
                it.context.startActivity(intent)
            }




        }
    }

}




