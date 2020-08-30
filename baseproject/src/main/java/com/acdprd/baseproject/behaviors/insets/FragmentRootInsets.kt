package com.acdprd.baseproject.behaviors.insets

import android.view.View
import com.acdprd.baseproject.utils.extensions.addSystemTopBottomPadding
import com.acdprd.baseproject.utils.extensions.addSystemTopPadding


/**
 * by acdprd | 13.07.2020.
 */

class FragmentRootInsets(var onlyTop: Boolean) : InsetsBehavior {
    override fun applyInsets(view: View) {
        fragmentRootInsets(view)
    }

    private fun fragmentRootInsets(root: View) {
        if (onlyTop) {
            root.addSystemTopPadding(isConsumed = true)
        } else {
            root.addSystemTopBottomPadding(isConsumed = true)
        }
    }
}