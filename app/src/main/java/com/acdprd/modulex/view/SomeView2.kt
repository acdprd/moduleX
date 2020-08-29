package com.acdprd.modulex.view

import android.content.Context
import android.util.AttributeSet
import com.acdprd.adapterandviews.view.CustomListItemView
import com.acdprd.modulex.R
import com.acdprd.modulex.databinding.ViewSome2Binding
import com.acdprd.modulex.model.SomeModel2

class SomeView2 @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CustomListItemView<ViewSome2Binding, SomeModel2>(context, attrs, defStyleAttr) {

    override fun getLayoutRes(): Int = R.layout.view_some_2

    init {
        setMatchWrap()
    }

    override fun setData(model: SomeModel2) {
        val div = (model.value.toDouble() / 2)

        val divText = String.format("%10f", div)
        binding.tvText.text = divText
    }

}