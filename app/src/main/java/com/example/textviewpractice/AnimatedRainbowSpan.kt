package com.example.textviewpractice

import android.content.Context
import android.graphics.LinearGradient
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Shader
import android.text.TextPaint
import android.text.style.CharacterStyle
import android.text.style.UpdateAppearance
import android.R




/***
 * 彩虹
 */
class AnimatedRainbowSpan(pContext: Context) : CharacterStyle(), UpdateAppearance {

    private val mColors: IntArray = pContext.resources.getIntArray(com.example.textviewpractice.R.array.rainbowColors)
//    private val colors:IntArray = intArrayOf(R.array.rainbowColors)
    var translateXPercentage = 0f
    override fun updateDrawState(pTextPaint: TextPaint) {
        pTextPaint.style = Paint.Style.FILL
        val iWidth = pTextPaint.textSize * mColors.size
        val iShader: Shader = LinearGradient(
            0f, 0f, 0f, iWidth,
            mColors, null, Shader.TileMode.MIRROR
        )
        val iMatrix = Matrix()
        iMatrix.reset()
        iMatrix.setRotate(90f)
        iMatrix.postTranslate(
            iWidth * translateXPercentage, 0f
        )
        iShader.setLocalMatrix(iMatrix)
        pTextPaint.shader = iShader

    }

    @JvmName("setTranslateXPercentage1")
    fun setTranslateXPercentage(
        value: Float
    ) {
        translateXPercentage = value
    }

    @JvmName("getTranslateXPercentage1")
    fun getTranslateXPercentage(): Float {
        return translateXPercentage
    }
}


