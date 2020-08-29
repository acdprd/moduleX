package com.acdprd.adapterandviews.adapter.rv.holder

import androidx.databinding.ViewDataBinding
import com.acdprd.adapterandviews.view.CustomListItemView

class ItemHolder<B : ViewDataBinding, M : Any, V : CustomListItemView<B, M>>(customListItemView: V) :
    BaseItemHolder<V, M>(customListItemView) {

    override fun bindItem(model: M) {
        baseItemView.setData(model)
    }
}
