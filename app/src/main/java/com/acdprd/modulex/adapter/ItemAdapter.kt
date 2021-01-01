package com.acdprd.modulex.adapter

import androidx.databinding.ViewDataBinding
import com.acdprd.adapterandviews.adapter.rv.BaseListItemAdapter
import com.acdprd.adapterandviews.adapter.rv.holder.ItemHolder
import com.acdprd.adapterandviews.model.ViewTypeFinder
import com.acdprd.adapterandviews.model.interfaces.IFindViewType
import com.acdprd.adapterandviews.view.CustomListItemView
import com.acdprd.modulex.Const

abstract class ItemAdapter : BaseListItemAdapter<ListItem, Const.ViewType>() {
    override fun getViewTypeFinder(): IFindViewType<Const.ViewType> =
        ViewTypeFinder(Const.ViewType.values())

}

abstract class ItemBAdapter : BaseListItemBAdapter<ListItem, Const.ViewType>() {
    override fun getViewTypeFinder(): IFindViewType<Const.ViewType> =
        ViewTypeFinder(Const.ViewType.values())

}