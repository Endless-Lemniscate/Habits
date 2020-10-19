package com.example.habits

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habits.models.Habit
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private val data: MutableList<Habit> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewManager = LinearLayoutManager(this)
        viewAdapter = HabitsRecyclerAdapter(data)
        recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        add_habit_fab.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            startActivity(intent)
        }
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
}