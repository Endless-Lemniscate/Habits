package com.example.habits.ui.list

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.habits.R
import com.example.habits.listViewModelFactory
import kotlinx.android.synthetic.main.fragment_bottom_sheet.view.*

private lateinit var viewModel: ListViewModel

class BottomSheetFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(activity as AppCompatActivity, listViewModelFactory).get(
            ListViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false)

        view.add_new_habit_fab.setOnClickListener {
            findNavController().navigate(R.id.action_home_fragment_to_habit_details)
        }

        view.search_edit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //viewModel.setFilter(s.toString(), 0)
            }
        })

        view.up.setOnClickListener{
            //viewModel.setFilter(view.search_edit.text.toString(), 0)
        }

        view.down.setOnClickListener{
            //viewModel.setFilter(view.search_edit.text.toString(), 1)
        }

        return view
    }

}