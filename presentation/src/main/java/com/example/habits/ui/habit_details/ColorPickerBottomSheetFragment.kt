package com.example.habits.ui.habit_details

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.habits.R
import kotlinx.android.synthetic.main.fragment_color_picker_bottom_sheet.view.*


class ColorPickerBottomSheetFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_color_picker_bottom_sheet, container, false)

        //for rounded corners on views
        view.color_picker_frame.clipToOutline = true
        view.color_watch.clipToOutline = true

        //send color to viewModel by clicking on colorPicker
        view.color_picker.setOnClickListener {
            habitDetailsViewModel.setColorToColorShowingElement(it.color_picker.checkedColor)
        }

        //close bottomSheet by x button
        view.close.setOnClickListener {
            habitDetailsViewModel.hideColorPicker()
        }

        //observe color change for bottomSheet "color-showing" element
        habitDetailsViewModel.habit.observe(viewLifecycleOwner, {
            view.color_show.background = ColorDrawable(it.color)
            view.rgb_string.text = habitDetailsViewModel.getRgbString()
            view.hsv_string.text = habitDetailsViewModel.getHsvString()
        })

        return view
    }


}

