package com.acdprd.modulex.adapter

import android.content.Context
import androidx.databinding.ViewDataBinding
import com.acdprd.adapterandviews.view.CustomListItemView
import com.acdprd.modulex.Const
import com.acdprd.modulex.view.SomeView1
import com.acdprd.modulex.view.SomeView2

class SomeAdapter : ItemAdapter() {
    override fun getCustomView(
        context: Context,
        viewType: Const.ViewType?
    ): CustomListItemView<out ViewDataBinding, out ListItem>? {
        return when (viewType) {
            Const.ViewType.SOME_TYPE_1 -> SomeView1(context)
            Const.ViewType.SOME_TYPE_2 -> SomeView2(context)

            else -> null
        }
    }
}