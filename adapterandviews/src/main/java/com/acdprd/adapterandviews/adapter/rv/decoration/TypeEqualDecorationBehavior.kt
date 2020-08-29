package com.acdprd.adapterandviews.adapter.rv.decoration

import android.graphics.drawable.Drawable
import com.acdprd.adapterandviews.adapter.rv.BaseListItemAdapter
import com.acdprd.adapterandviews.model.interfaces.IListItem
import com.acdprd.adapterandviews.model.interfaces.IViewType


class TypeEqualDecorationBehavior<LIST_ITEM, VIEW_TYPE> :
    BaseListItemsDividersBehavior<VIEW_TYPE> where LIST_ITEM : IListItem<VIEW_TYPE>, VIEW_TYPE : IViewType {
    var adapter: BaseListItemAdapter<LIST_ITEM, VIEW_TYPE>? = null
    var getDivider: ((VIEW_TYPE?, VIEW_TYPE?) -> Drawable?)? = null
    var getOffset: ((VIEW_TYPE?, VIEW_TYPE?) -> Int)? = null

    override fun /*<VIEW_TYPE : IViewType>*/ getDivider(
        currentItemType: VIEW_TYPE?,
        nextItemType: VIEW_TYPE?
    ): Drawable? {
        return when {
            getDivider != null -> getDivider?.invoke(currentItemType, nextItemType)
            else -> null
        }
    }

    override fun /*<VIEW_TYPE : IViewType>*/ getOffset(
        currentItemType: VIEW_TYPE?,
        nextItemType: VIEW_TYPE?
    ): Int {
        getOffset?.let {
            return it.invoke(currentItemType, nextItemType)
        } ?: let {
            return 0
        }
    }
}