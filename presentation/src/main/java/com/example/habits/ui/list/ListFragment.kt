package com.example.habits.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.habits.R
import com.example.habits.listViewModelFactory
import com.example.domain.model.Habit
import com.example.domain.usecases.SyncStatus
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.view.*


private lateinit var viewModel: ListViewModel
private lateinit var recyclerAdapter: HabitsRecyclerAdapter
lateinit var recyclerView: RecyclerView
private lateinit var viewManager: LinearLayoutManager

class ListFragment: Fragment(), CellClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(activity as AppCompatActivity, listViewModelFactory).get(
            ListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        viewManager = LinearLayoutManager(view.context)
        recyclerAdapter = HabitsRecyclerAdapter(view.context, this)
        recyclerView = view.recycler_view
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = recyclerAdapter
        }

        ItemTouchHelper( object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(recyclerView: RecyclerView, viewHolder: ViewHolder,
                target: ViewHolder): Boolean { return false }

            override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
                val habit: Habit = recyclerAdapter.getHabitAt(viewHolder.adapterPosition)
                viewModel.deleteHabit(habit)
            }
        }).attachToRecyclerView(recyclerView)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        class MyObserver : Observer<List<Habit>> {
            override fun onChanged(items: List<Habit>) {
                recyclerAdapter.submitList(items)
            }
        }
        viewModel.listHabits.observe(viewLifecycleOwner, MyObserver())

        //show string message on toast
        viewModel.toastMessage .observe(viewLifecycleOwner, {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })

        //set string sync_status
        viewModel.syncStatus.observe(viewLifecycleOwner, {syncStatus ->
            when(syncStatus) {
                SyncStatus.Offline -> sync_status_view.text = "Режим оффлайн"
                is SyncStatus.InProgress -> sync_status_view.text = "Синхронизация ${syncStatus.remain}"
                is SyncStatus.Error -> sync_status_view.text = "Ошибка ${syncStatus.error.message}"
                SyncStatus.Success -> sync_status_view.text = "Синхронизировано"
            }
        })

    }

    override fun onCellClickListener(habit_id: Int) {
        val bundle = Bundle()
        bundle.putInt("habit_id", habit_id)
        findNavController().navigate(R.id.action_home_fragment_to_habit_details, bundle)
    }

    override fun habitDoneClickListener(habit: Habit) {
        viewModel.accomplishHabit(habit)
    }

    override fun onPause() {
        super.onPause()

        //save scroll position
        val scroll = viewManager.findFirstCompletelyVisibleItemPosition()
        viewModel.saveScrollState(scroll)
    }

    override fun onResume() {
        super.onResume()

        //restore scroll position
        val scroll = viewModel.scrollState.value
        scroll?.let{ viewManager.scrollToPosition(it) }
    }

}