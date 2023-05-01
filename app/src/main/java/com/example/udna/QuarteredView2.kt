package com.example.udna


import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.core.view.GestureDetectorCompat


class QuarteredView2(context: Context?, attrs: AttributeSet?) : View(context, attrs),GestureDetector.OnGestureListener {

    private var gridPaint = Paint().apply { color = Color.BLACK;style = Paint.Style.STROKE; strokeWidth = 2.0f }
    private var numberPaint = Paint().apply { ;color = Color.WHITE ;style = Paint.Style.FILL_AND_STROKE
        ;textAlign = Paint.Align.CENTER }
    var backgroundPaint = Paint().apply {color = Color.DKGRAY; style = Paint.Style.FILL}

    private var default = Paint().apply{color=(Color.parseColor("#424242")); style = Paint.Style.FILL}
    private var grass = Paint().apply{color=(Color.parseColor("#A3E2AC")); style = Paint.Style.FILL}
    private var dirt = Paint().apply{color=(Color.parseColor("#61300e")) ; style = Paint.Style.FILL}
    private var white = Paint().apply{color=Color.WHITE; style = Paint.Style.FILL}
    private var black = Paint().apply{color=Color.BLACK; style = Paint.Style.FILL}
    private var enemyred = Paint().apply{color=(Color.parseColor("#940c15")) ; style = Paint.Style.FILL}
    private var friendblue = Paint().apply{color=(Color.parseColor("#0e19e8")) ; style = Paint.Style.FILL}
    private var playerpurple = Paint().apply{color=(Color.parseColor("#FF6200EE")) ; style = Paint.Style.FILL}
    private var playerpink = Paint().apply{color=(Color.parseColor("#FFBB86FC")) ; style = Paint.Style.FILL}
    private var playerorange = Paint().apply{color=(Color.parseColor("#e89c0e")) ; style = Paint.Style.FILL}

    //LEGEND:
    // 0 = dkgray,
    // 1 = green, grass
    // 2 = brown, dirt
    // 3 = white
    // 4 = black
    // 5 = red
    // 6 = blue
    //players
    // 7 = purple
    // 8 = pink
    // 9 = orange

    private var squareSize = 0.0f
    private var offsetx = 0.0f
    private var offsety = 0.0f

    private var mDetector = GestureDetectorCompat(this.context, this)
    var myArray = Array(9){IntArray(9)}
    var currentcolor = 0

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
            drawRect(
                offsetx,
                offsety,
                offsetx + squareSize * 8.0f,
                offsety + squareSize * 8.0f,
                backgroundPaint
            )
            drawRect(
                offsetx,
                offsety,
                offsetx + squareSize * 8.0f,
                offsety + squareSize * 8.0f,
                gridPaint
            )
            for (i in 0..7) {
                drawLine(
                    offsetx, offsety + i * squareSize, offsetx + squareSize * 8.0f,
                    offsety + i * squareSize, gridPaint
                )
                drawLine(
                    offsetx + i * squareSize, offsety, offsetx + i * squareSize,
                    offsety + squareSize * 8.0f, gridPaint
                )
            }

            for (i in 0..7) {
                for (j in 0..7) {
                    when(myArray[i][j]){
                        0 ->{
                            drawRect(
                                offsetx + (j) * (squareSize+.5f), offsety + (i) * (squareSize+.5f), offsetx + (j+1) * (squareSize-.5f),
                                offsety + (i+1) * (squareSize-.5f), default
                            )
                        }
                        1 ->{
                            drawRect(
                                offsetx + (j) * (squareSize+.5f), offsety + (i) * (squareSize+.5f), offsetx + (j+1) * (squareSize-.5f),
                                offsety + (i+1) * (squareSize-.5f), grass
                            )
                        }
                        2 ->{
                            drawRect(
                                offsetx + (j) * (squareSize+.5f), offsety + (i) * (squareSize+.5f), offsetx + (j+1) * (squareSize-.5f),
                                offsety + (i+1) * (squareSize-.5f), dirt
                            )
                        }
                        3 ->{
                            drawRect(
                                offsetx + (j) * (squareSize+.5f), offsety + (i) * (squareSize+.5f), offsetx + (j+1) * (squareSize-.5f),
                                offsety + (i+1) * (squareSize-.5f), white
                            )
                        }
                        4 ->{
                            drawRect(
                                offsetx + (j) * (squareSize+.5f), offsety + (i) * (squareSize+.5f), offsetx + (j+1) * (squareSize-.5f),
                                offsety + (i+1) * (squareSize-.5f), black
                            )
                        }
                        5 ->{
                            drawRect(
                                offsetx + (j) * (squareSize+.5f), offsety + (i) * (squareSize+.5f), offsetx + (j+1) * (squareSize-.5f),
                                offsety + (i+1) * (squareSize-.5f), enemyred
                            )
                        }
                        6 ->{
                            drawRect(
                                offsetx + (j) * (squareSize+.5f), offsety + (i) * (squareSize+.5f), offsetx + (j+1) * (squareSize-.5f),
                                offsety + (i+1) * (squareSize-.5f), friendblue
                            )
                        }
                        7 ->{
                            drawRect(
                                offsetx + (j) * (squareSize+.5f), offsety + (i) * (squareSize+.5f), offsetx + (j+1) * (squareSize-.5f),
                                offsety + (i+1) * (squareSize-.5f), playerpurple
                            )
                        }
                        8 ->{
                            drawRect(
                                offsetx + (j) * (squareSize+.5f), offsety + (i) * (squareSize+.5f), offsetx + (j+1) * (squareSize-.5f),
                                offsety + (i+1) * (squareSize-.5f), playerpink
                            )
                        }
                        9 ->{
                            drawRect(
                                offsetx + (j) * (squareSize+.5f), offsety + (i) * (squareSize+.5f), offsetx + (j+1) * (squareSize-.5f),
                                offsety + (i+1) * (squareSize-.5f), playerorange
                            )
                        }
                    }
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


        myArray[row][col] = currentcolor
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

