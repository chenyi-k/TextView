package com.example.textviewpractice

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Html
import androidx.core.content.ContextCompat

//Html 圖片抓取資源
class ResourceImageGetter(context: Context) : Html.ImageGetter {
    // Constructor takes a Context
    private val context: Context = context
    override fun getDrawable(source: String?): Drawable? {
        val path: Int = context.resources.getIdentifier(
            source, "drawable", context.packageName
        )
        val drawable = ContextCompat.getDrawable(context, path)
        drawable?.setBounds(
            0, 0,
            drawable.intrinsicWidth,
            drawable.intrinsicHeight
        )
        return drawable
    }
}