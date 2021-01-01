package com.acdprd.modulex.view.basevb

import android.content.Context
import android.util.AttributeSet
import androidx.databinding.ViewDataBinding
import androidx.viewbinding.ViewBinding

abstract class CustomListItemBView<B : ViewBinding, M : Any> @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CustomBView<B>(context, attrs, defStyleAttr) {

    abstract fun setData(model: M)
}