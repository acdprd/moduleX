package com.acdprd.modulex.model

import com.acdprd.modulex.Const
import com.acdprd.modulex.adapter.ListItem

class SomeModel1(var text:String?): ListItem{

    override fun getItemType(): Const.ViewType = Const.ViewType.SOME_TYPE_1
}