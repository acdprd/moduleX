package com.acdprd.adapterandviews.model.interfaces

interface IListItem<VIEW_TYPE : IViewType> {
    fun getItemType(): VIEW_TYPE
}