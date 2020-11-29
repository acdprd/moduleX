package com.acdprd.adapterandviews.adapter.rv.decoration

import android.graphics.drawable.Drawable
import androidx.annotation.Dimension
import com.acdprd.adapterandviews.adapter.rv.BaseListItemAdapter
import com.acdprd.adapterandviews.model.interfaces.IListItem
import com.acdprd.adapterandviews.model.interfaces.IViewType

class BetweenEqualsTypeOnlyDecoration<LIST_ITEM, VIEW_TYPE>(
    var adapter: BaseListItemAdapter<LIST_ITEM, VIEW_TYPE>,
    var type: VIEW_TYPE?,
    var divider: Drawable,
    @Dimension var offset: Int = divider.minimumHeight,
    var exceptTypes: List<VIEW_TYPE> = mutableListOf()
) : MainItemsDividersDecoration<VIEW_TYPE>(TypeEqualDecorationBehavior<LIST_ITEM, VIEW_TYPE>().also { b ->
    b.adapter = adapter

    b.getDivider = { prev, next ->
        when {
            type == null && (prev as IViewType == next) && (exceptTypes.none { it == prev || it == next }) -> divider
            type != null && (prev as IViewType == type && next as IViewType == type) -> divider
            else -> null
        }
    }
    b.getOffset = { prev, next ->
        when {
            type == null && (prev as IViewType == next) && (exceptTypes.none { it == prev || it == next }) -> offset
            type != null && (prev as IViewType == type && next as IViewType == type) -> offset
            else -> 0
        }
    }
}) where LIST_ITEM : IListItem<VIEW_TYPE>, VIEW_TYPE : IViewType, VIEW_TYPE : Enum<VIEW_TYPE>{

    fun a(){
        viewTypeFinder
    }
}
