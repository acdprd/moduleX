package com.acdprd.adapterandviews.view

import android.content.Context
import androidx.databinding.ViewDataBinding
import android.util.AttributeSet

abstract class CustomListItemView<B : ViewDataBinding, M : Any> @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CustomView<B>(context, attrs, defStyleAttr) {

    abstract fun setData(model: M)
}
