package com.example.habits

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioGroup
import androidx.core.view.get
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.activity_edit.color
import kotlinx.android.synthetic.main.activity_edit.description
import kotlinx.android.synthetic.main.activity_edit.name
import kotlinx.android.synthetic.main.activity_edit.period
import kotlinx.android.synthetic.main.activity_edit.priority

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
    }

}