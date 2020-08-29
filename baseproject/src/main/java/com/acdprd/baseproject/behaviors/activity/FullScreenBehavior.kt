package com.acdprd.baseproject.behaviors.activity

import android.app.Activity
import android.os.Build
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.acdprd.baseproject.behaviors.activity.ActivityBehavior.Companion.TRANSPARENT

/**
 * by acdprd | 13.07.2020.
 */

class FullScreenBehavior(@ColorRes val navigationBarColorRes: Int = TRANSPARENT) :
    ActivityBehavior {

    override fun init(activity: Activity) {
        fullScreen(activity)
    }

    private fun fullScreen(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.apply {
                decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    decorView.systemUiVisibility = decorView.systemUiVisibility or
                            View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                }

                navigationBarColor = ContextCompat.getColor(context, navigationBarColorRes)
                StatusBarColorBehavior().init(activity)
            }
        }
    }

}