package com.acdprd.adapterandviews.adapter.rv.decoration

interface DividerOffsetsBehavior {
    fun getLeftOffset(position: Int): Int
    fun getTopOffset(position: Int): Int
    fun getRightOffset(position: Int): Int
    fun getBottomOffset(position: Int): Int
}