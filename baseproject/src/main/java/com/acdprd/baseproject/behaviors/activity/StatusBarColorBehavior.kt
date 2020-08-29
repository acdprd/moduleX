package com.acdprd.baseproject.behaviors.activity

import android.app.Activity
import android.os.Build
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.acdprd.baseproject.behaviors.activity.ActivityBehavior.Companion.TRANSPARENT

class StatusBarColorBehavior(@ColorRes val statusBarColor: Int = TRANSPARENT) : ActivityBehavior {

    override fun init(activity: Activity) {
        setStatusBarColor(activity)
    }

    private fun setStatusBarColor(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.window.statusBarColor = ContextCompat.getColor(activity, statusBarColor)
            activity.window.decorView.systemUiVisibility =
                activity.window.decorView.systemUiVisibility or
                        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }
}