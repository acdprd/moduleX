package com.acdprd.adapterandviews.adapter.rv.decoration

import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import androidx.annotation.ColorInt

class SimpleDividerDrawablesBehavior(@field:ColorInt private val dividerColor: Int) :
    DividerDrawablesBehavior {
    private var divider: GradientDrawable? = null

    init {
        initDivider()
    }

    private fun initDivider() {
        divider = GradientDrawable()
        divider?.setColor(dividerColor)
    }

    override fun getLeftDivider(position: Int): Drawable? {
        return divider
    }

    override fun getTopDivider(position: Int): Drawable? {
        return divider
    }

    override fun getRightDivider(position: Int): Drawable? {
        return divider
    }

    override fun getBottomDivider(position: Int): Drawable? {
        return divider
    }
}