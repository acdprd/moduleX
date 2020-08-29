package com.acdprd.adapterandviews.adapter.rv.decoration

import android.graphics.drawable.Drawable
import androidx.annotation.Dimension
import com.acdprd.adapterandviews.adapter.rv.BaseListItemAdapter
import com.acdprd.adapterandviews.model.interfaces.IListItem
import com.acdprd.adapterandviews.model.interfaces.IViewType

/**
 * by acdprd | 25.06.2020.
 */

class IfAnyOfTypesEqualTypesDecoration<LIST_ITEM, VIEW_TYPE>(
    var adapter: BaseListItemAdapter<LIST_ITEM, VIEW_TYPE>?,
    var types: List<VIEW_TYPE>,
    var divider: Drawable?,
    @Dimension var offset: Int = divider?.minimumHeight ?: 0,
    var exceptTypes: List<VIEW_TYPE> = mutableListOf()
) : MainItemsDividersDecoration<VIEW_TYPE>(
    TypeEqualDecorationBehavior<LIST_ITEM, VIEW_TYPE>().also {
        it.adapter = adapter
        it.getDivider = { prev, next ->
            val toCheck = mutableListOf(prev, next)
            when {
                (toCheck.any { outer -> types.any { inner -> inner == outer } }) && (exceptTypes.none { it == prev || it == next }) -> divider
                else -> null
            }
        }
        it.getOffset = { prev, next ->
            val toCheck = mutableListOf(prev, next)
            when {
                (toCheck.any { outer -> types.any { inner -> inner == outer } }) && (exceptTypes.none { it == prev || it == next }) -> offset
                else -> 0
            }
        }


    },
    adapter?.getViewTypeFinder()
) where LIST_ITEM : IListItem<VIEW_TYPE>, VIEW_TYPE : IViewType, VIEW_TYPE : Enum<VIEW_TYPE> {
    var withFirstTopDivider: Boolean = true

//    constructor(
//        adapter: BaseListItemAdapter<LIST_ITEM, VIEW_TYPE>,
//        types: List<VIEW_TYPE>,
//        context: Context?,
//        exceptTypes: List<VIEW_TYPE> = mutableListOf()
//    ) : this(
//        adapter,
//        types,
//        if (context != null) ResourcesCompat.getDrawable(
//            context.resources,
//            DEFAULT_BACKGROUND,
//            null
//        ) else null,
//        exceptTypes = exceptTypes
//    )

    override fun getTopOffset(position: Int): Int {
        return if (withFirstTopDivider && position == 0 && orientation == ORIENTATION_VERTICAL) {
            offset
        } else {
            0
        }
    }

    override fun getTopDivider(position: Int): Drawable? {
        return if (withFirstTopDivider && position == 0 && orientation == ORIENTATION_VERTICAL) {
            getDividerForPosition(0)
        } else {
            null
        }
    }

    companion object {
//        const val DEFAULT_BACKGROUND = R.drawable.de
    }
}