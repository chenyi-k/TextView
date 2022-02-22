package com.example.textviewpractice

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.EditText

/***
 * 帶橫線的 EditText
 * 參考：https://www.itread01.com/content/1546503326.html
 */

@SuppressLint("AppCompatCustomView")
class LineEditText(pContext: Context?, pAttrs: AttributeSet?) : EditText(pContext, pAttrs) {
    private var mPaint: Paint = Paint()

    override fun onDraw(pCanvas: Canvas) {
        val iStartX = paddingLeft.toFloat() //開始位置
        val iStopX = (width - paddingRight).toFloat() //結束位置
        val iOffsetY: Float = paddingTop - paint.fontMetrics.top + mPaint.strokeWidth * 2 //行間距
        for (iI in 0 until lineCount) {
            val y = iOffsetY + lineHeight * iI
            pCanvas.drawLine(iStartX, y, iStopX, y, mPaint)
        }
        super.onDraw(pCanvas)
    }

    init {
        //建立畫筆
        //設定 橫線寬度、描邊、預設顏色、線冒樣式
        mPaint.strokeWidth = (lineHeight / 10).toFloat()
        mPaint.style = Paint.Style.STROKE
        mPaint.color = Color.BLACK
        mPaint.strokeCap = Paint.Cap.ROUND

    }
}
