package com.example.habits.ui.habit_details.views

import android.content.Context
import android.text.InputType
import android.util.AttributeSet

//custom autocomplete for dropdown lists with no filtering and input_type null
class AutoCompleteNoFilter : androidx.appcompat.widget.AppCompatAutoCompleteTextView {
    constructor(context: Context) : super(context) {}
    constructor(arg0: Context, arg1: AttributeSet?) : super(arg0, arg1) {}
    constructor(arg0: Context, arg1: AttributeSet?, arg2: Int) : super(arg0, arg1, arg2) {}

    init { inputType = InputType.TYPE_NULL }

    override fun enoughToFilter(): Boolean {
        return false
    }
}