package com.acdprd.baseproject.behaviors.insets

import android.util.Log
import android.view.View
import androidx.core.graphics.Insets
import androidx.core.view.WindowInsetsCompat
import com.acdprd.baseproject.helpers.extensions.doOnApplyWindowInsets
import com.acdprd.baseproject.helpers.extensions.updatePaddings


/**
 * by acdprd | 13.07.2020.
 */

class RootInsetsBehavior : InsetsBehavior {
    override fun applyInsets(view: View) {
        rootInsets(view)
    }

    private fun rootInsets(root: View) {
        root.doOnApplyWindowInsets { v, insets, initialPadding ->

//            Log.w(tag(),"ROOT B c:${v.tag()}/${root.tag()} l:${insets.systemWindowInsetLeft} t:${insets.systemWindowInsetTop} r:${insets.systemWindowInsetRight} b:${insets.systemWindowInsetBottom} | ${initialPadding}")
            v.updatePaddings(
                left = initialPadding.left + insets.systemWindowInsetLeft,
                right = initialPadding.right + insets.systemWindowInsetRight
            )
            WindowInsetsCompat.Builder().setSystemWindowInsets(
                Insets.of(
                    0,
                    insets.systemWindowInsetTop,
                    0,
                    insets.systemWindowInsetBottom
                )
            ).build()
        }
    }
}