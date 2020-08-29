package com.acdprd.adapterandviews.adapter.rv.decoration

class SimpleDividerOffsetsBehavior(
    private val leftOffset: Int,
    private val topOffset: Int,
    private val rightOffset: Int,
    private val bottomOffset: Int
) : DividerOffsetsBehavior {

    constructor(offset: Int) : this(offset, offset, offset, offset) {}

    override fun getLeftOffset(position: Int): Int {
        return leftOffset
    }

    override fun getTopOffset(position: Int): Int {
        return topOffset
    }

    override fun getRightOffset(position: Int): Int {
        return rightOffset
    }

    override fun getBottomOffset(position: Int): Int {
        return bottomOffset
    }

}