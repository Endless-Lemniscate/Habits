package com.example.habits.ui.habit_details.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View


class ColorPicker(context: Context, attrs: AttributeSet) : View(context, attrs){
    var checkedColor = 0

    private val colorsCount = 16
    private val paintGradient = Paint()
    private val p = Paint()

    private val rect = RectF()
    private val hsvKeyColors = IntArray(7)

    init {
        //set hsv colors
        for(i in 0..6){
            val hsv = floatArrayOf((i * 60).toFloat(), 1f, 1f)
            val rgb = Color.HSVToColor(hsv)
            hsvKeyColors[i] = rgb
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(widthMeasureSpec, widthMeasureSpec/colorsCount)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.apply {

            //hsv gradient
            paintGradient.shader = LinearGradient(0f,
                0f,
                width.toFloat(),
                0f,
                hsvKeyColors,
                null,
                Shader.TileMode.CLAMP
            )


            val size = width.toFloat()/colorsCount
            val padding = size/6
            val rectSize = size - 2*padding

            //draw gradient
            rect.set(0f, 0f, width.toFloat(), height.toFloat())
            drawRect(rect, paintGradient)

            var startX = padding
            for(i in 0 until colorsCount) {
                //set rect size and position
                rect.set(startX, padding, startX+rectSize, padding + rectSize)

                //get rect color from rect position and draw it
                val centerOfRect = (startX + rectSize/2)/width.toFloat()*360f
                val hsv = floatArrayOf(centerOfRect, 1f, 1f)
                val rgb = Color.HSVToColor(hsv)

                p.style = Paint.Style.FILL
                p.color = rgb
                drawRect(rect, p)

                p.strokeWidth = 2f
                p.style = Paint.Style.STROKE
                p.color = Color.WHITE
                drawRect(rect, p)

                startX += 2*padding + rectSize
            }

        }
    }

    private var startClickTime: Long = 0
    private val maxClickDuration = 150

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var clicked = false

        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                startClickTime = event.eventTime
                return true
            }
            MotionEvent.ACTION_UP -> {
                val clickDuration = event.eventTime - startClickTime
                if (clickDuration < maxClickDuration) {
                    clicked = true
                }
            }
            else -> clicked = false
        }

        if(clicked) {
            val colorNumber = (event.x/width*colorsCount).toInt()
            val colorNumberHSV = colorNumber.toFloat()/colorsCount*360f
            val hsv = floatArrayOf(colorNumberHSV, 1f, 1f)
            val rgb = Color.HSVToColor(hsv)
            checkedColor = rgb
            callOnClick()
        }

        return super.onTouchEvent(event)
    }


}