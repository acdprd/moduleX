package com.acdprd.adapterandviews.adapter.rv.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseItemHolder<V : View,M:Any>(var baseItemView: V) :
    RecyclerView.ViewHolder(baseItemView) {

    abstract fun bindItem(model: M)
}