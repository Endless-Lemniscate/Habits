package com.example.habits


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habits.models.Habit
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.android.synthetic.main.fragment_list.view.*

private const val ARG_ISNEW = "isNew"
private const val ARG_INDEX = "index"

lateinit var recyclerView: RecyclerView
private lateinit var viewManager: RecyclerView.LayoutManager
lateinit var viewAdapter: RecyclerView.Adapter<*>


class ListFragment : Fragment() {
    private var isNew: Boolean = false
    private var index: Int? = null
    private var which: String? = null

    private lateinit var communicator: Communicator
    var callback: ListFragmentCallBack? = null

    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
        callback = activity as ListFragmentCallBack
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        communicator = activity as Communicator
        arguments?.let {
            isNew = it.getBoolean(ARG_ISNEW)
            index = it.getInt(ARG_INDEX)
            which = it.getString("which")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)


        val dataNew = when (which) {
            "good" -> ArrayList(data.filter{ it.habit_type })
            "bad" -> ArrayList(data.filter{ !it.habit_type })
            else -> data
        }


        viewManager = LinearLayoutManager(view.context)
        viewAdapter = HabitsRecyclerAdapter(dataNew, activity)
        recyclerView = view.recycler_view
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        view.add_habit_fab.setOnClickListener {
            it.add_habit_fab.transitionName = "shared_element_container"
            val bundle = Bundle()
            bundle.apply {
                putBoolean("isNew", true)
                putInt("index", 0)
                putString("name", "")
                putString("description", "")
                putInt("frequency", 1)
                putString("period", "День")
                putBoolean("habitType", true)
                putInt("priority", 1)
                putInt("color", 0)
            }
            communicator.passDataToHabit(bundle, view.add_habit_fab)
        }

        return view
    }




    override fun onResume() {
        super.onResume()
        callback?.onFragmentViewResumed()
    }

    companion object {
        @JvmStatic
        fun newInstance(isNew: Boolean, index: Int, which: String) =
            ListFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_ISNEW, isNew)
                    putInt(ARG_INDEX, index)
                    putString("which", which)
                }
            }
    }
}

interface ListFragmentCallBack {
    fun onFragmentViewResumed()
}