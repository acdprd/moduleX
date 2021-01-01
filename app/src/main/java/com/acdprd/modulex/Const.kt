package com.acdprd.modulex

import com.acdprd.adapterandviews.model.interfaces.IViewType

object Const {

    enum class ViewType : IViewType{
        UNDEFINED,
        SOME_TYPE_1,
        SOME_TYPE_2,
        SOME_TYPE_3,
        SOME_TYPE_4,;

        override fun getType(): Int = ordinal
    }
}