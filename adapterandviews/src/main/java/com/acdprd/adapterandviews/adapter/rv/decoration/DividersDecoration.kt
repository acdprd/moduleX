package com.acdprd.adapterandviews.adapter.rv.decoration

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView

open class DividersDecoration : RecyclerView.ItemDecoration {
    private var dividerOffsetsBehavior: DividerOffsetsBehavior? = null
    private var dividerDrawablesBehavior: DividerDrawablesBehavior? = null
    private val mBounds = Rect()

    constructor() {
        setDividerOffsetsBehavior(generateSimpleOffsetsBehavior(0))
    }

    constructor(offset: Int, @ColorInt color: Int) {
        setDividerOffsetsBehavior(generateSimpleOffsetsBehavior(offset))
        setDividerDrawablesBehavior(generateSimpleDrawablesBehavior(color))
    }

    constructor(
        leftOffset: Int,
        topOffset: Int,
        rightOffset: Int,
        bottomOffset: Int,
        @ColorInt color: Int
    ) {
        setDividerOffsetsBehavior(
            generateSimpleOffsetsBehavior(
                leftOffset,
                topOffset,
                rightOffset,
                bottomOffset
            )
        )
        setDividerDrawablesBehavior(generateSimpleDrawablesBehavior(color))
    }

    constructor(dividerOffsetsBehavior: DividerOffsetsBehavior, @ColorInt color: Int) {
        setDividerOffsetsBehavior(dividerOffsetsBehavior)
        setDividerDrawablesBehavior(generateSimpleDrawablesBehavior(color))
    }

    constructor(
        dividerOffsetsBehavior: DividerOffsetsBehavior,
        dividerDrawablesBehavior: DividerDrawablesBehavior?
    ) {
        setDividerOffsetsBehavior(dividerOffsetsBehavior)
        setDividerDrawablesBehavior(dividerDrawablesBehavior)
    }

    fun setDividerOffsetsBehavior(dividerOffsetsBehavior: DividerOffsetsBehavior) {
        this.dividerOffsetsBehavior = dividerOffsetsBehavior
    }

    fun setDividerDrawablesBehavior(dividerDrawablesBehavior: DividerDrawablesBehavior?) {
        this.dividerDrawablesBehavior = dividerDrawablesBehavior
    }

    override fun onDraw(
        c: Canvas,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (parent.layoutManager == null || dividerDrawablesBehavior == null) {
            return
        }
        draw(c, parent)
    }

    private fun draw(
        canvas: Canvas,
        parent: RecyclerView
    ) {
        if (dividerDrawablesBehavior == null) {
            return
        }
        canvas.save()
        if (parent.clipToPadding) {
            canvas.clipRect(
                parent.paddingLeft,
                parent.paddingTop,
                parent.width - parent.paddingRight,
                parent.height - parent.paddingBottom
            )
        }
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val adapterPosition = parent.getChildAdapterPosition(child)
            parent.getDecoratedBoundsWithMargins(child, mBounds)
            drawLeftDivider(canvas, adapterPosition)
            drawTopDivider(canvas, adapterPosition)
            drawRightDivider(canvas, adapterPosition)
            drawBottomDivider(canvas, adapterPosition)
        }
        canvas.restore()
    }

    private fun drawLeftDivider(
        canvas: Canvas,
        adapterPosition: Int
    ) {
        dividerDrawablesBehavior?.let {
            val mDivider = dividerDrawablesBehavior!!.getLeftDivider(adapterPosition) ?: return
            val leftOffset: Int = dividerOffsetsBehavior?.getLeftOffset(adapterPosition) ?: 0
            if (leftOffset > 0) {
                val right = mBounds.left + leftOffset
                var left = mBounds.left
                if (mDivider.intrinsicWidth in 0 until leftOffset) {
                    left = right - leftOffset
                }
                mDivider.setBounds(left, mBounds.top, right, mBounds.bottom)
                mDivider.draw(canvas)
            }
        }
    }

    private fun drawTopDivider(canvas: Canvas, adapterPosition: Int) {
        dividerDrawablesBehavior?.let {
            val mDivider = dividerDrawablesBehavior?.getTopDivider(adapterPosition) ?: return
            val topOffset: Int = dividerOffsetsBehavior?.getTopOffset(adapterPosition) ?: 0
            if (topOffset > 0) {
                val bottom = mBounds.top + topOffset
                var top = mBounds.top
                if (mDivider.intrinsicHeight in 0 until topOffset) {
                    top = bottom - mDivider.intrinsicHeight
                }
                mDivider.setBounds(mBounds.left, top, mBounds.right, bottom)
                mDivider.draw(canvas)
            }
        }

    }

    private fun drawRightDivider(
        canvas: Canvas,
        adapterPosition: Int
    ) {
        dividerDrawablesBehavior?.let {
            val mDivider = dividerDrawablesBehavior?.getRightDivider(adapterPosition) ?: return
            val rightOffset: Int = dividerOffsetsBehavior?.getRightOffset(adapterPosition) ?: 0
            if (rightOffset > 0) {
                val left = mBounds.right - rightOffset
                var right = mBounds.right
                if (mDivider.intrinsicWidth in 0 until rightOffset) {
                    right = left + mDivider.intrinsicWidth
                }
                mDivider.setBounds(left, mBounds.top, right, mBounds.bottom)
                mDivider.draw(canvas)
            }
        }
    }

    private fun drawBottomDivider(
        canvas: Canvas,
        adapterPosition: Int
    ) {
        dividerDrawablesBehavior?.let {
            val mDivider = dividerDrawablesBehavior?.getBottomDivider(adapterPosition) ?: return
            val bottomOffset: Int = dividerOffsetsBehavior?.getBottomOffset(adapterPosition) ?: 0
            if (bottomOffset > 0) {
                val top = mBounds.bottom - bottomOffset
                var bottom = mBounds.bottom
                if (mDivider.intrinsicHeight in 0 until bottomOffset) {
                    bottom = top + mDivider.intrinsicHeight
                }
                mDivider.setBounds(mBounds.left, top, mBounds.right, bottom)
                mDivider.draw(canvas)
            }
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val itemPosition = parent.getChildAdapterPosition(view)
        outRect[dividerOffsetsBehavior?.getLeftOffset(itemPosition)
            ?: 0, dividerOffsetsBehavior?.getTopOffset(
            itemPosition
        ) ?: 0, dividerOffsetsBehavior?.getRightOffset(itemPosition) ?: 0] =
            dividerOffsetsBehavior?.getBottomOffset(itemPosition) ?: 0
    }

    private fun generateSimpleDrawablesBehavior(@ColorInt color: Int): DividerDrawablesBehavior {
        return SimpleDividerDrawablesBehavior(color)
    }

    private fun generateSimpleOffsetsBehavior(offset: Int): DividerOffsetsBehavior {
        return SimpleDividerOffsetsBehavior(offset)
    }

    private fun generateSimpleOffsetsBehavior(
        leftOffset: Int,
        topOffset: Int,
        rightOffset: Int,
        bottomOffset: Int
    ): DividerOffsetsBehavior {
        return SimpleDividerOffsetsBehavior(leftOffset, topOffset, rightOffset, bottomOffset)
    }
}