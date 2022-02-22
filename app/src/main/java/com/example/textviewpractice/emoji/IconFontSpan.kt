package com.example.textviewpractice.emoji

import android.content.Context
import android.text.TextPaint
import android.graphics.Typeface
import android.text.style.MetricAffectingSpan


class IconFontSpan(pContext: Context) : MetricAffectingSpan() {
    override fun updateMeasureState(pTextPaint: TextPaint) {
        pTextPaint.typeface = typeface
    }

    override fun updateDrawState(pTextPaint: TextPaint) {
        pTextPaint.typeface = typeface
    }

    companion object {
        private var typeface: Typeface? = null
    }

    init {
        if (typeface == null) {
            typeface = Typeface.createFromAsset(
                pContext.assets, "fonts/icomoon.ttf"
            )
        }
    }
}