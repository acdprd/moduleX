package com.acdprd.modulex.adapter

import android.content.Context
import androidx.viewbinding.ViewBinding
import com.acdprd.adapterandviews.adapter.rv.BaseItemAdapter
import com.acdprd.adapterandviews.model.interfaces.IFindViewType
import com.acdprd.adapterandviews.model.interfaces.IListItem
import com.acdprd.adapterandviews.model.interfaces.IViewType
import com.acdprd.modulex.view.basevb.CustomListItemBView

abstract class BaseListItemBAdapter<LIST_ITEM, VIEW_TYPE : IViewType> :
    BaseItemAdapter<LIST_ITEM, CustomListItemBView<ViewBinding, LIST_ITEM>, ItemBHolder<ViewBinding, LIST_ITEM, CustomListItemBView<ViewBinding, LIST_ITEM>>>() where LIST_ITEM : IListItem<VIEW_TYPE> {

    abstract fun getViewTypeFinder(): IFindViewType<VIEW_TYPE>

//    protected abstract fun getCustomView(
//        context: Context,
//        viewType: VIEW_TYPE?
//    ): CustomListItemView<*, *>?

    protected abstract fun getCustomView(
        context: Context,
        viewType: VIEW_TYPE?
    ): CustomListItemBView<out ViewBinding, out LIST_ITEM>?

    override fun getCustomView(
        context: Context,
        viewType: Int
    ): CustomListItemBView<ViewBinding, LIST_ITEM>? {
        return getCustomView(
            context,
            getViewTypeFinder().find(viewType)
        ) as? CustomListItemBView<ViewBinding, LIST_ITEM>
    }

    override fun getItemViewType(position: Int): Int {
        return getViewTypeFinder().find(_items.getOrNull(position)?.getItemType())
    }

    override fun getViewHolder(view: CustomListItemBView<ViewBinding, LIST_ITEM>): ItemBHolder<ViewBinding, LIST_ITEM, CustomListItemBView<ViewBinding, LIST_ITEM>> {
        return ItemBHolder(view)
    }

    fun removeAllExcept(vararg types: LIST_ITEM) {
        for (item in _items.reversed()) {
            if (types.none { it == item.getItemType() }) {
                remove(item)
            }
        }
    }
}