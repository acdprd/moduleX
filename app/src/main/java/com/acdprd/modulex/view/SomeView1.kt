package com.acdprd.modulex.view

import android.content.Context
import android.util.AttributeSet
import com.acdprd.adapterandviews.view.CustomListItemView
import com.acdprd.modulex.R
import com.acdprd.modulex.databinding.ViewSome1Binding
import com.acdprd.modulex.model.SomeModel1

class SomeView1 @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CustomListItemView<ViewSome1Binding, SomeModel1>(context, attrs, defStyleAttr) {
    override fun getLayoutRes(): Int = R.layout.view_some_1

    init {
        setMatchWrap()
    }

    override fun setData(model: SomeModel1) {
        binding.tvText.text = model.text
    }

}