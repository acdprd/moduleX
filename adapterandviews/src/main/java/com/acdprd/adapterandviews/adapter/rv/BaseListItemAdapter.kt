package com.acdprd.adapterandviews.adapter.rv

import android.content.Context
import androidx.databinding.ViewDataBinding
import com.acdprd.adapterandviews.adapter.rv.holder.ItemHolder
import com.acdprd.adapterandviews.model.interfaces.IFindViewType
import com.acdprd.adapterandviews.model.interfaces.IListItem
import com.acdprd.adapterandviews.model.interfaces.IViewType
import com.acdprd.adapterandviews.view.CustomListItemView

abstract class BaseListItemAdapter<LIST_ITEM, VIEW_TYPE : IViewType> :
    BaseItemAdapter<LIST_ITEM, CustomListItemView<ViewDataBinding, LIST_ITEM>, ItemHolder<ViewDataBinding, LIST_ITEM, CustomListItemView<ViewDataBinding, LIST_ITEM>>>() where LIST_ITEM : IListItem<VIEW_TYPE> {

    abstract fun getViewTypeFinder(): IFindViewType<VIEW_TYPE>

//    protected abstract fun getCustomView(
//        context: Context,
//        viewType: VIEW_TYPE?
//    ): CustomListItemView<*, *>?

    protected abstract fun getCustomView(
        context: Context,
        viewType: VIEW_TYPE?
    ): CustomListItemView<out ViewDataBinding, out LIST_ITEM>?

    override fun getCustomView(
        context: Context,
        viewType: Int
    ): CustomListItemView<ViewDataBinding, LIST_ITEM>? {
        return getCustomView(context, getViewTypeFinder().find(viewType)) as? CustomListItemView<ViewDataBinding, LIST_ITEM>
    }

    override fun getItemViewType(position: Int): Int {
        return getViewTypeFinder().find(_items.getOrNull(position)?.getItemType())
    }

    override fun getViewHolder(view: CustomListItemView<ViewDataBinding, LIST_ITEM>): ItemHolder<ViewDataBinding, LIST_ITEM, CustomListItemView<ViewDataBinding, LIST_ITEM>> {
        return ItemHolder(view)
    }

    fun removeAllExcept(vararg types: LIST_ITEM) {
        for (item in _items.reversed()) {
            if (types.none { it == item.getItemType() }) {
                remove(item)
            }
        }
    }
}