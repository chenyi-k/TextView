package com.example.textviewpractice

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.style.ReplacementSpan

/***
 * 自定義 Span
 */
class Span:ReplacementSpan() {
    private var mWidth: Int = 0
    private var mPaint: Paint? = Paint()


    override fun getSize(pPaint: Paint, pText: CharSequence?, pStart: Int, pEnd: Int, fm: Paint.FontMetricsInt?): Int {
        mWidth = pPaint.measureText(pText, pStart, pEnd).toInt()
        return mWidth
    }


    override fun draw(pCanvas: Canvas, pText: CharSequence?, pStart: Int, pEnd: Int, pX: Float, pTop: Int, pY: Int, pBottom: Int, pPaint: Paint) {
        mPaint?.style = Paint.Style.STROKE
        mPaint?.color = Color.BLUE
        mPaint?.isAntiAlias = true
        mPaint?.strokeWidth = 3f
        mPaint?.let { pCanvas.drawRect(pX, pTop.toFloat(), pX + mWidth, pBottom.toFloat(), it) }
    }
}

