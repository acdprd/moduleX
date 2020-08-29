package com.acdprd.adapterandviews.model

import com.acdprd.adapterandviews.model.interfaces.IFindViewType
import com.acdprd.adapterandviews.model.interfaces.IViewType

class ViewTypeFinder<E>(var enums: Array<E>, var defaultType: E? = enums.getOrNull(0)) :
    IFindViewType<E> where E : Enum<E>, E : IViewType {

    override fun find(type: Int): E? {
        return enums.find { it.getType() == type } ?: defaultType
    }

    override fun find(viewType: E?): Int {
        return viewType?.ordinal?:0
    }


}