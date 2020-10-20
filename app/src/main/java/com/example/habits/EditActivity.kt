package com.example.habits

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.activity_edit.color
import kotlinx.android.synthetic.main.activity_edit.description
import kotlinx.android.synthetic.main.activity_edit.name
import kotlinx.android.synthetic.main.activity_edit.period
import kotlinx.android.synthetic.main.activity_edit.times
import kotlinx.android.synthetic.main.activity_edit.type_of_habit


class EditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        button_save.setOnClickListener {
            startActivityWithData()
        }

        validateName()


        val adapter = ArrayAdapter(
            priority_dropdown.context,
            R.layout.dropdown_menu_popup_item,
            arrayOf("Высокий", "Средний", "Низкий")
        )

        priority_dropdown.apply{
            setAdapter(adapter)
            inputType = InputType.TYPE_NULL //set not editable
            setText("Высокий", false) //set default value
        }

        val adapterPeriod = ArrayAdapter(
            period.context,
            R.layout.dropdown_menu_popup_item,
            arrayOf("Час", "6 часов", "День", "3 дня", "Неделя", "Месяц", "2 месяца")
        )

        period.apply{
            setAdapter(adapterPeriod)
            inputType = InputType.TYPE_NULL //set not editable
            setText("День", false) //set default value
        }

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
    }


    override fun onResume() {
        super.onResume()
        val intent = intent

        if (intent?.extras != null){
            index.setText(intent.getIntExtra("index", 0).toString())
            name.setText(intent.getStringExtra("name"))
            description.setText(intent.getStringExtra("description"))
            priority_dropdown.setText( when(intent.getIntExtra("priority", 0)){
                1 -> "Высокий"
                2 -> "Средний"
                3 -> "Низкий"
                else -> "Без приоритета"
            }, false)

            type_of_habit.check(
                when(intent.getStringExtra("type_of_habit")){
                    "good" -> R.id.good
                    "bad" -> R.id.bad
                    else -> R.id.bad
                }
            )

            times.setText(intent.getStringExtra("times"))
            period.setText(intent.getStringExtra("period"), false)

            button_save.text = "Редактировать"
            title = "Редактировать"

            //val color: Int = intent.getIntExtra("color", 0)
        }
    }


    private fun validateName(){
        name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.isNotEmpty()) button_save.isEnabled = true
            }
        })
    }

    private fun startActivityWithData(){
        val intent: Intent = Intent(this, MainActivity::class.java)
            .apply {
                putExtra("index", index.text.toString().toInt())
                putExtra("name", name.text.toString())
                putExtra("description", description.text.toString())
                putExtra("priority",
                    when (priority_dropdown.text.toString()) {
                        "Высокий" -> 1
                        "Средний" -> 2
                        "Низкий" -> 3
                        else -> 0
                    })

                putExtra("type_of_habit",
                    when (type_of_habit.checkedRadioButtonId) {
                        R.id.good -> good.transitionName
                        R.id.bad -> bad.transitionName
                        else -> "None"
                    })

                putExtra("times", times.text.toString().toInt())
                putExtra("period", period.text.toString())
                putExtra("color", color.text.toString().toInt())
            }
        startActivity(intent)
    }

}

