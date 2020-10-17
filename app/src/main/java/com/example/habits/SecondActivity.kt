package com.example.habits

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_second.*
import java.lang.Math.pow
import kotlin.math.pow

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        textView2.text = (intent.getStringExtra("number").toDouble().pow(2.0)).toString()
    }
}