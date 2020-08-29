package com.acdprd.baseproject.behaviors.activity

import android.app.Activity
import com.acdprd.baseproject.R

interface ActivityBehavior {
    fun init(activity: Activity)

    companion object {
        const val TRANSPARENT = android.R.color.transparent
    }
}