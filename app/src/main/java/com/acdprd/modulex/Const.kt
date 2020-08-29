package com.acdprd.modulex

import com.acdprd.adapterandviews.model.interfaces.IViewType

object Const {

    enum class ViewType : IViewType{
        UNDEFINED,
        SOME_TYPE_1,
        SOME_TYPE_2,;

        override fun getType(): Int = ordinal
    }
}