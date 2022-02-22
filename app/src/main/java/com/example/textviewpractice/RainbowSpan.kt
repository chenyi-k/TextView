package com.example.textviewpractice

import android.content.Context
import android.graphics.LinearGradient
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Shader
import android.text.TextPaint
import android.text.style.CharacterStyle
import android.text.style.UpdateAppearance

/***
 * 彩虹
 */
class RainbowSpan(pContext: Context) : CharacterStyle(), UpdateAppearance {

    private val mColors: IntArray = pContext.resources.getIntArray(R.array.rainbowColors)
//    private val colors:IntArray = intArrayOf(R.array.rainbowColors)

    override fun updateDrawState(paint: TextPaint) {
        paint.style = Paint.Style.FILL
        val shader: Shader = LinearGradient(
            0f, 0f, paint.textSize, paint.textSize * mColors.size,
            mColors, null, Shader.TileMode.MIRROR
        )
        val matrix = Matrix()
        matrix.setRotate(90f)
        shader.setLocalMatrix(matrix)
        paint.shader = shader
    }


}