package com.example.textviewpractice

import android.content.Intent
import android.provider.Settings
import android.text.style.ClickableSpan
import android.view.View

/***
 * 字符 鏈接 ClickableSpan
 */
class GoToSettingsSpan : ClickableSpan() {
    override fun onClick(pView: View) {
        pView.context.startActivity(Intent(Settings.ACTION_SETTINGS))
    }
}