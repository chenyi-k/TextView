package com.example.textviewpractice.fraction

import android.text.Editable
import android.text.Html
import android.text.Spannable
import org.xml.sax.XMLReader


class FractionTagHandler : Html.TagHandler {
    override fun handleTag(
        opening: Boolean,
        tag: String?, output: Editable, xmlReader: XMLReader?
    ) {
        if (!"afrc".equals(tag, ignoreCase = true)) return
        val len: Int = output.length
        if (opening) {
            output.setSpan(
                FractionSpan(), len, len,
                Spannable.SPAN_MARK_MARK
            )
        } else {
            val obj = getLast(output, FractionSpan::class.java)
            val where = output.getSpanStart(obj)
            output.removeSpan(obj)
            if (where != len) {
                output.setSpan(FractionSpan(), where, len, 0)
            }
        }
    }

    private fun getLast(text: Editable, kind: Class<FractionSpan>): Any? {
        val objs = text.getSpans(0, text.length, kind)
        if (objs.isEmpty()) return null
        for (i in objs.indices.reversed()) {
            if (text.getSpanFlags(objs[i]) == Spannable.SPAN_MARK_MARK) {
                return objs[i]
            }
        }
        return null
    }

}