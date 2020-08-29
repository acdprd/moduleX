package com.acdprd.adapterandviews.adapter.rv.decoration

import android.graphics.drawable.Drawable

interface DividerDrawablesBehavior {
    fun getLeftDivider(position: Int): Drawable?
    fun getTopDivider(position: Int): Drawable?
    fun getRightDivider(position: Int): Drawable?
    fun getBottomDivider(position: Int): Drawable?
}