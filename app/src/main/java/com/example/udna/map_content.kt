package com.example.udna

import android.content.Context
import android.content.Intent
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Button
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.GestureDetectorCompat
import kotlin.math.min

class map_content : View, GestureDetector.OnGestureListener {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    private var gridPaint = Paint().apply { color = Color.BLACK;style = Paint.Style.STROKE; strokeWidth = 2.0f }
    private var numberPaint = Paint().apply { ;color = Color.BLACK ;style = Paint.Style.FILL_AND_STROKE
        ;textAlign = Paint.Align.CENTER }

    private var squareSize = 0.0f
    private var offsetx = 0.0f
    private var offsety = 0.0f

    private var mDetector = GestureDetectorCompat(this.context, this)
    private val myArray = Array(9){IntArray(9)}


    override fun onMeasure(width: Int, height: Int) {
        super.onMeasure(width, height)
        val size= measuredWidth.coerceAtMost(measuredHeight)
        squareSize= size.toFloat()/9.0f // must be square
        offsetx=(measuredWidth-size)/2.0f
        offsety=(measuredHeight-size)/2.0f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.apply {
            drawRect(offsetx, offsety, offsetx + squareSize * 9.0f, offsety + squareSize * 9.0f, gridPaint)
            for (i in 0..8) {
                drawLine(offsetx, offsety + i * squareSize, offsetx + squareSize * 9.0f,
                    offsety + i * squareSize, gridPaint)
                drawLine(offsetx + i * squareSize, offsety, offsetx + i * squareSize,
                    offsety + squareSize * 9.0f, gridPaint)
            }

            for (i in 0..8) {
                for (j in 0..8) {
                    val bounds = Rect()
                    numberPaint.getTextBounds("0", 0, 1, bounds)
                    val fontSize = (squareSize/9.0f)*3.5f
                    numberPaint.textSize = fontSize * resources.displayMetrics.density
                    drawText(myArray[j][i].toString(), offsetx + (i + 0.5f) * squareSize,
                        offsety + (j + 0.5f) * squareSize + bounds.height() / 2.0f,
                        numberPaint
                    )
                }
            }
        }
    }
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if(mDetector.onTouchEvent(event)) {
            return true
        }
        return super.onTouchEvent(event)

    }
    override fun onDown(p0: MotionEvent): Boolean {
        return true
    }

    override fun onShowPress(p0: MotionEvent) {
    }

    override fun onSingleTapUp(p0: MotionEvent): Boolean {
        val x = p0.x - offsetx
        val y = p0.y - offsety

        val row = (y/squareSize).toInt()
        val col = (x/squareSize).toInt()


        myArray[row][col] = (myArray[row][col]+1)%10
        invalidate()
        return true
    }

    override fun onScroll(p0: MotionEvent, p1: MotionEvent, p2: Float, p3: Float): Boolean {
        return false
    }

    override fun onLongPress(p0: MotionEvent) {
    }

    override fun onFling(p0: MotionEvent, p1: MotionEvent, p2: Float, p3: Float): Boolean {
        return false
    }

}