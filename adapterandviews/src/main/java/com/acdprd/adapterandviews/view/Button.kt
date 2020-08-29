package com.acdprd.adapterandviews.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.ResourcesCompat

open class Button @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    AppCompatImageView(context, attrs, defStyleAttr) {

    init {
        scaleType = ScaleType.CENTER_INSIDE
        setBackgroundBorderless()
    }

    protected open fun setBackgroundBorderless() {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(
            android.R.attr.selectableItemBackgroundBorderless,
            typedValue,
            true
        )
        if (typedValue.resourceId != 0) {
            this.setBackgroundResource(typedValue.resourceId)
        }
    }

    open fun setIcon(drawable: Drawable) {
        setImageDrawable(drawable)
    }

    open fun setIcon(@DrawableRes icon: Int) {
        setImageDrawable(ResourcesCompat.getDrawable(context.resources, icon, null))
    }
}
