package com.example.androidmediaplayer

import android.content.Context

object HandleWidthHeight {
    fun intToDP(dp: Int, context: Context): Int {
        val density = context.resources.displayMetrics.density
        return Math.round(dp.toFloat() * density)
    }
}