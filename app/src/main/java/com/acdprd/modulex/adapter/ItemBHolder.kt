package com.acdprd.modulex.adapter

import androidx.databinding.ViewDataBinding
import androidx.viewbinding.ViewBinding
import com.acdprd.adapterandviews.adapter.rv.holder.BaseItemHolder
import com.acdprd.adapterandviews.view.CustomListItemView
import com.acdprd.modulex.view.basevb.CustomListItemBView

class ItemBHolder<B : ViewBinding, M : Any, V : CustomListItemBView<B, M>>(customListItemView: V) :
    BaseItemHolder<V, M>(customListItemView) {

    override fun bindItem(model: M) {
        baseItemView.setData(model)
    }
}