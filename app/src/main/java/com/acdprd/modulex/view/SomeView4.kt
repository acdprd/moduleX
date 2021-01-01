package com.acdprd.modulex.view

import android.content.Context
import android.util.AttributeSet
import com.acdprd.modulex.R
import com.acdprd.modulex.databinding.ViewSome4Binding
import com.acdprd.modulex.fragment.basevb.viewBindingDelegate
import com.acdprd.modulex.model.SomeModel4
import com.acdprd.modulex.view.basevb.CustomListItemBView

class SomeView4 @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CustomListItemBView<ViewSome4Binding, SomeModel4>(context, attrs, defStyleAttr) {

    override val binding: ViewSome4Binding by viewBindingDelegate()
    override fun getLayoutRes(): Int = R.layout.view_some_4

    init {
        setMatchWrap()
    }

    override fun setData(model: SomeModel4) {
        binding.tvText.text = model.value.toInt().toString()
    }
}