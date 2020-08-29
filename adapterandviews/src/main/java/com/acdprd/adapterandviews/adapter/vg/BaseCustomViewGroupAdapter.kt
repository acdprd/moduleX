package com.acdprd.adapterandviews.adapter.vg

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.acdprd.adapterandviews.model.interfaces.IListItem
import com.acdprd.adapterandviews.model.interfaces.IViewType
import com.acdprd.adapterandviews.view.CustomListItemView

abstract class BaseCustomViewGroupAdapter<LIST_ITEM, VIEW_TYPE : IViewType> constructor() :
    BaseViewGroupAdapter<LIST_ITEM>() where LIST_ITEM : IListItem<VIEW_TYPE> {

    constructor(items: List<LIST_ITEM>) : this() {
        this._items = items.toMutableList()
    }

    constructor(viewGroup: ViewGroup?) : this() {
        setViewGroup(viewGroup)
    }

    constructor(viewGroup: ViewGroup?, items: List<LIST_ITEM>) : this() {
        this._items = items.toMutableList()
        setViewGroup(viewGroup)
    }

    override fun createView(context: Context, item: LIST_ITEM): View? {
        val view = createCustomView(context, item.getItemType())
        view?.let {
            bindItem(view, item)
        }
        return view
    }

    protected abstract fun createCustomView(
        context: Context,
        viewType: VIEW_TYPE
    ): CustomListItemView<*, *>?

    protected open fun bindItem(
        customListItemView: CustomListItemView<*, *>,
        item: LIST_ITEM
    ) {
        (customListItemView as? CustomListItemView<*, LIST_ITEM>)?.let {
            customListItemView.setData(item)
        }
        customListItemView.setOnClickListener { v: View? ->
            itemClickHandler?.invoke(item)
        }
    }

    fun findViewType(position: Int): VIEW_TYPE? {
        if (position < 0) {
            return null
        }
        if (position >= itemCount) {
            return null
        }
        val item = getItem(position)

        return when (item) {
            is LIST_ITEM -> {
                item.getItemType()
            }
            else -> {
                null
            }
        }
    }
}