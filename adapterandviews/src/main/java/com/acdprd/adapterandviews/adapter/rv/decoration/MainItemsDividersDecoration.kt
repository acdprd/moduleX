package com.acdprd.adapterandviews.adapter.rv.decoration

import android.graphics.drawable.Drawable
import androidx.annotation.IntRange
import com.acdprd.adapterandviews.model.ViewTypeFinder
import com.acdprd.adapterandviews.model.interfaces.IFindViewType
import com.acdprd.adapterandviews.model.interfaces.IViewType

open class MainItemsDividersDecoration<E>(
    var listItemsDividersBehavior: BaseListItemsDividersBehavior<E>,
    @IntRange(
        from = 0,
        to = 1
    ) var orientation: Int,
        var viewTypeFinder: IFindViewType<E>?
) : DividersDecoration(), DividerOffsetsBehavior,
    DividerDrawablesBehavior where E : Enum<E>, E : IViewType {


    constructor(listItemsDividersBehavior: BaseListItemsDividersBehavior<E>) : this(
        listItemsDividersBehavior,
        ORIENTATION_VERTICAL,
        null
    )

    constructor(listItemsDividersBehavior: BaseListItemsDividersBehavior<E>,viewTypeFinder: IFindViewType<E>?) : this(
        listItemsDividersBehavior,
        ORIENTATION_VERTICAL,
        viewTypeFinder
    )

    init {
        init()
    }

    private fun init() {
        setDividerDrawablesBehavior(this)
        setDividerOffsetsBehavior(this)
    }

    override fun getLeftDivider(position: Int): Drawable? {
        return null
    }

    override fun getTopDivider(position: Int): Drawable? {
        return null
    }

    override fun getRightDivider(position: Int): Drawable? {
        return if (orientation == ORIENTATION_HORIZONTAL) {
            getDividerForPosition(position)
        } else null
    }

    override fun getBottomDivider(position: Int): Drawable? {
        return if (orientation == ORIENTATION_VERTICAL) {
            getDividerForPosition(position)
        } else null
    }

    protected fun getDividerForPosition(position: Int): Drawable? {
        val currentType: E? = viewTypeFinder?.find(position)
        val nextType: E? = viewTypeFinder?.find(position + 1)
        return listItemsDividersBehavior.getDivider(currentType, nextType)
    }

    override fun getLeftOffset(position: Int): Int {
        return 0
    }

    override fun getTopOffset(position: Int): Int {
        return 0
    }

    override fun getRightOffset(position: Int): Int {
        return if (orientation == ORIENTATION_HORIZONTAL) {
            getOffsetForPosition(position)
        } else 0
    }

    override fun getBottomOffset(position: Int): Int {
        return if (orientation == ORIENTATION_VERTICAL) {
            getOffsetForPosition(position)
        } else 0
    }

    protected fun getOffsetForPosition(position: Int): Int {
        val currentType: E? = viewTypeFinder?.find(position)
        val nextType: E? = viewTypeFinder?.find(position + 1)
        return listItemsDividersBehavior.getOffset(currentType, nextType)
    }


    companion object {
        const val ORIENTATION_VERTICAL = 0
        const val ORIENTATION_HORIZONTAL = 1

    }
}