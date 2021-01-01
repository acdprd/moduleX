package com.acdprd.modulex.view

import android.content.Context
import android.util.AttributeSet
import com.acdprd.modulex.R
import com.acdprd.modulex.databinding.ViewSome3Binding
import com.acdprd.modulex.fragment.basevb.viewBindingDelegate
import com.acdprd.modulex.model.SomeModel3
import com.acdprd.modulex.view.basevb.CustomListItemBView

class SomeView3 @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CustomListItemBView<ViewSome3Binding, SomeModel3>(context, attrs, defStyleAttr) {

    override val binding: ViewSome3Binding by viewBindingDelegate()
    override fun getLayoutRes(): Int = R.layout.view_some_3

    init {
        setMatchWrap()
    }

    override fun setData(model: SomeModel3) {
        binding.tvText.text = model.text
    }
}