package com.example.habits

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_edit.*



class EditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        button_save.setOnClickListener {
            val intent: Intent = Intent(this, MainActivity::class.java)
                .apply {
                    putExtra("name", name.text.toString())
                    putExtra("description", description.text.toString())
                    putExtra("priority", priority.selectedItemId.toInt())
                    putExtra(
                        "type_of_habit",
                                when (type_of_habit.checkedRadioButtonId) {
                                    R.id.good -> good.transitionName
                                    R.id.bad -> bad.transitionName
                                    else -> "None"
                                })
                    putExtra("period", period.text.toString().toInt())
                    putExtra("color", color.text.toString().toInt())
                }
            startActivity(intent)
        }


        val countries = arrayOf("Высокий", "Средний", "Низкий")

        val adapter = ArrayAdapter(
            filled_exposed_dropdown.context,
            R.layout.dropdown_menu_popup_item,
            countries
        )

        filled_exposed_dropdown.setAdapter(adapter)














    }

}