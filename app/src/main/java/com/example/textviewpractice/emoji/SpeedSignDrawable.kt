package com.example.textviewpractice.emoji

import android.annotation.SuppressLint
import android.graphics.*
import android.graphics.drawable.Drawable
import android.widget.TextView


class SpeedSignDrawable(pTextView: TextView, private val pNumber: String): Drawable() {
    private val mAscent: Float = pTextView.paint.ascent()
    private val mDescent: Float = pTextView.paint.descent() //字符底部到baseLine的距離
    private val mTextSize: Float = pTextView.textSize
    private val mStrokeWidth: Float = pTextView.paint.strokeWidth
    private val mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mSize = (-mAscent).toInt() //字符頂部到ascent的距離

    init {
        this.setBounds(0, 0, mSize, mSize)
    }

    override fun draw(pCanvas: Canvas) {
        drawCircleFill(pCanvas)
        drawCircleStroke(pCanvas)
        drawText(pCanvas)
    }
    private fun drawCircleFill(pCanvas: Canvas){
        // Draw the circle
        mPaint.style = Paint.Style.FILL
        mPaint.color = Color.YELLOW
        pCanvas.drawCircle((mSize / 2).toFloat(), (mSize / 1.5).toFloat(), (mSize / 2).toFloat(), mPaint)//16, 21
    }

    private fun drawCircleStroke(pCanvas: Canvas){
        mPaint.style = Paint.Style.STROKE
        mPaint.color = Color.RED
        val ringWidth = (mSize / 10).toFloat()
        mPaint.strokeWidth = ringWidth
        pCanvas.drawCircle((mSize / 2).toFloat(), (mSize / 1.5).toFloat(), mSize / 2 - ringWidth / 2, mPaint)//16, 20, 半徑3
    }

    private fun drawText(pCanvas: Canvas){
        val ratio = 0.4f //字的大小
        mPaint.style = Paint.Style.FILL
        mPaint.color = Color.BLACK
        mPaint.textAlign = Paint.Align.CENTER
        mPaint.textSize = mTextSize * ratio
        mPaint.strokeWidth = mStrokeWidth
        mPaint.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
        val x = mSize / 2
        val y = (mSize / 1.5 - (mDescent + mAscent) / 1.5 * ratio).toInt()
        pCanvas.drawText(pNumber, x.toFloat(), y.toFloat(), mPaint)// 輸入文字, 16, 20
    }

    override fun setAlpha(alpha: Int) {}

    override fun setColorFilter(colorFilter: ColorFilter?) {}

    @SuppressLint("WrongConstant")
    override fun getOpacity(): Int {return 0}

}

