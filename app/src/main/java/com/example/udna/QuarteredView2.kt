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
import androidx.core.view.GestureDetectorCompat


class QuarteredView2(context: Context?, attrs: AttributeSet?) : View(context, attrs),GestureDetector.OnGestureListener {

    private var gridPaint = Paint().apply { color = Color.BLACK;style = Paint.Style.STROKE; strokeWidth = 2.0f }
    private var numberPaint = Paint().apply { ;color = Color.WHITE ;style = Paint.Style.FILL_AND_STROKE
        ;textAlign = Paint.Align.CENTER }
    var backgroundPaint = Paint().apply {color = Color.DKGRAY; style = Paint.Style.FILL}

    private var default = Paint().apply{color=Color.DKGRAY; style = Paint.Style.FILL}
    private var grass = Paint().apply{color=(Color.parseColor("#A3E2AC")); style = Paint.Style.FILL}
    private var dirt = Paint().apply{color=(Color.parseColor("#E2C1A3")) ; style = Paint.Style.FILL}

    //LEGEND:
    // 0 = dkgray,nothing
    // 1 = green, grass



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
                        3 ->{
                            drawRect(
                                offsetx + (j) * squareSize, offsety + (i) * squareSize, offsetx + (j+1) * squareSize,
                                offsety + (i+1) * squareSize, grass
                            )
                        }
                        1 ->{
                            drawCircle(
                                offsetx + (j + 0.5f) * squareSize, offsety + (i + 0.5f) * squareSize,
                                squareSize / 2.5f, grass
                            )
                        }
                        2 ->{
                            drawCircle(
                                offsetx + (j + 0.5f) * squareSize, offsety + (i + 0.5f) * squareSize,
                                squareSize / 2.5f, dirt
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

