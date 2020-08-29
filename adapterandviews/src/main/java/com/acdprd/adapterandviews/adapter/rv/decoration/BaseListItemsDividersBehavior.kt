package com.acdprd.adapterandviews.adapter.rv.decoration

import android.graphics.drawable.Drawable
import com.acdprd.adapterandviews.model.interfaces.IViewType


interface BaseListItemsDividersBehavior <VIEW_TYPE : IViewType> {
    fun /*<VIEW_TYPE : IViewType>*/ getDivider(
        currentItemType: VIEW_TYPE?,
        nextItemType: VIEW_TYPE?
    ): Drawable?

    fun /*<VIEW_TYPE : IViewType>*/ getOffset(currentItemType: VIEW_TYPE?, nextItemType: VIEW_TYPE?): Int
}