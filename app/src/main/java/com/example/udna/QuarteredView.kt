package com.example.udna
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat

class QuarteredViewq (context: Context?, attrs: AttributeSet?) : View(context, attrs),GestureDetector.OnGestureListener {
    private var colorSelection = arrayOf(arrayOf(0,1),arrayOf(2,3))
    private var quadColors = arrayOf(Color.GREEN,Color.BLUE,Color.RED,Color.YELLOW)
    private var mWidth : Float = 0.0f
    private var mHeight : Float = 0.0f
    private var mMidWidth : Float = 0.0f
    private var mMidHeight : Float = 0.0f

    private var mDetector = GestureDetectorCompat(this.context,this)

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (mDetector.onTouchEvent(event)) {
            return true
        }
        return super.onTouchEvent(event)
    }


    override fun onSizeChanged (w: Int, h: Int, oldw: Int, oldh: Int){
        super.onSizeChanged(w,h,oldw,oldh)
        mWidth = w.toFloat()
        mHeight = h.toFloat()
        mMidWidth = mWidth /2
        mMidHeight = mHeight / 2

    }

    override fun onDraw (canvas:Canvas){
        super.onDraw(canvas)
        val paint = Paint()
        val mTextPaint = Paint()
        mTextPaint.color = Color.BLACK
        mTextPaint.style = Paint.Style.FILL_AND_STROKE
        mTextPaint.textAlign = Paint.Align.CENTER
        mTextPaint.textSize = 40f;
        canvas.drawText("0", 0.0f, 0.0f, mTextPaint)

        paint.color = quadColors[colorSelection[0][0]]
        canvas.drawRect(0.0f,0.0f,mMidWidth,mMidHeight,paint)

        paint.color = quadColors[colorSelection[0][1]]
        canvas.drawRect(mMidWidth,0.0f,mWidth,mMidHeight,paint)

        paint.color = quadColors[colorSelection[1][0]]
        canvas.drawRect(0.0f,mMidHeight,mMidWidth,mHeight,paint)

        paint.color = quadColors[colorSelection[1][1]]
        canvas.drawRect(mMidWidth,mMidHeight,mWidth,mHeight,paint)

        paint.color = Color.BLACK
        paint.strokeWidth = 3.0f
        canvas.drawLine(0.0f,mMidHeight,mWidth,mMidHeight,paint)
        canvas.drawLine(mMidWidth, 0.0f,mMidWidth,mHeight,paint)
    }

    override fun onDown(p0: MotionEvent): Boolean {
        return true
    }

    override fun onShowPress(p0: MotionEvent) {

    }

    override fun onSingleTapUp(p0: MotionEvent): Boolean {
        var col = if (p0.x < mMidWidth) 0 else 1
        var row = if (p0.y < mMidHeight) 0 else 1
        colorSelection[row][col] = (colorSelection[row][col]+1)%quadColors.size
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