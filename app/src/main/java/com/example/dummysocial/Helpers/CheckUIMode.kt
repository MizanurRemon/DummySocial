package com.example.dummysocial.Helpers

import android.content.Context
import android.content.res.Configuration

fun isNightMode(context: Context): Boolean {
    val nightModeFlags: Int =
        context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    return nightModeFlags == Configuration.UI_MODE_NIGHT_YES
}