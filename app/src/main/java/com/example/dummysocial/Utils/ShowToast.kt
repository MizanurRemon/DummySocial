package com.example.dummysocial.Utils

import android.app.Activity
import android.content.Context
import androidx.core.content.res.ResourcesCompat
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

object ShowToast {

    fun successToast(context: Context, message: String) {
        MotionToast.createToast(
            context as Activity,
            "SUCCESS",
            message,
            MotionToastStyle.SUCCESS,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.LONG_DURATION,
            ResourcesCompat.getFont(context, www.sanju.motiontoast.R.font.helvetica_regular)
        )

    }


    fun errorToast(context: Context, message: String) {
        MotionToast.createToast(
            context as Activity,
            "ERROR",
            message,
            MotionToastStyle.ERROR,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.LONG_DURATION,
            ResourcesCompat.getFont(context, www.sanju.motiontoast.R.font.helvetica_regular)
        )
    }
}