package com.example.habits

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habits.models.Habit
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private val data: MutableList<Habit> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewManager = LinearLayoutManager(this)
        viewAdapter = HabitsRecyclerAdapter(data)
        recyclerView = recycler_view
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        add_habit_fab.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            startActivity(intent)
        }

        setRecyclerViewItemTouchListener()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        addExtraToDataSet(intent)
    }


    private fun addExtraToDataSet(intent: Intent?){
        if (intent?.extras != null) {
            val name: String = intent.getStringExtra("name") ?: "Null"
            val description: String = intent.getStringExtra("description") ?: "Null"
            val priority: Int = intent.getIntExtra("priority", 0)
            val type_of_habit: String = intent.getStringExtra("type_of_habit") ?: "Null"
            val period: Int = intent.getIntExtra("period", 0)
            val color: Int = intent.getIntExtra("color", 0)

            data.add(
                Habit(
                    name,
                    description,
                    priority,
                    type_of_habit,
                    period,
                    color
                )
            )
            viewAdapter.notifyDataSetChanged()
        }
    }

    private fun setRecyclerViewItemTouchListener() {
        val itemTouchCallback =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    viewHolder1: RecyclerView.ViewHolder
                ): Boolean {

                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val position = viewHolder.adapterPosition
                data.removeAt(position)
                recyclerView.adapter!!.notifyItemRemoved(position)
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

}