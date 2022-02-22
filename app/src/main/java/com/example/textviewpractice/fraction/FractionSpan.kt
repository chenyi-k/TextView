package com.example.textviewpractice.fraction

import android.text.TextPaint
import android.text.style.MetricAffectingSpan


class FractionSpan : MetricAffectingSpan() {
    override fun updateMeasureState(textPaint: TextPaint) {
        textPaint.fontFeatureSettings = "afrc"
    }

    override fun updateDrawState(textPaint: TextPaint) {
        textPaint.fontFeatureSettings = "afrc"
    }
}
