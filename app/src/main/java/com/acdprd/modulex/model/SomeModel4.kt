package com.acdprd.modulex.model

import com.acdprd.modulex.Const
import com.acdprd.modulex.adapter.ListItem

class SomeModel4(var value: Number) : ListItem {

    override fun getItemType(): Const.ViewType = Const.ViewType.SOME_TYPE_4
}