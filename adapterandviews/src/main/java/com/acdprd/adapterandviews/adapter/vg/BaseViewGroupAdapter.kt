package com.acdprd.adapterandviews.adapter.vg

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import kotlin.math.min

abstract class BaseViewGroupAdapter<T : Any> constructor() {
    protected var _items: MutableList<T> = mutableListOf()
    protected var vg: ViewGroup? = null
    var itemClickHandler: ((T)->Unit)? = null

    constructor(items: List<T>) : this() {
        this._items = items.toMutableList()
    }

    constructor(viewGroup: ViewGroup?) : this() {
        setViewGroup(viewGroup)
    }

    constructor(viewGroup: ViewGroup?, items: List<T>) : this() {
        this._items = items.toMutableList()
        setViewGroup(viewGroup)
    }

    val itemCount: Int
        get() = _items.size

    abstract fun createView(context: Context, item: T): View?

    open fun getItems(): List<T> = _items

    fun getItem(position: Int): T? = _items.getOrNull(position)

    fun setViewGroup(viewGroup: ViewGroup?) {
        this.vg = viewGroup
        generateList()
    }

    protected fun generateList() {
        vg?.let {
            for (i in 0 until itemCount) {
                addViewToList(i)
            }
        }
    }

    protected fun addViewToList(position: Int) {
        vg?.let {
            _items.getOrNull(position)?.let { item ->
                val view = createView(it.context, item)

                view?.let {
                    addViewToViewGroup(it, position)
                } ?: let {
                    logViewIfNull(position, item)
                }
            }
        }
    }

    protected open fun addViewToViewGroup(view: View, position: Int) {
        vg?.addView(view, position)
    }

    protected open fun logViewIfNull(position: Int, item: T?) {
        Log.w(
            this.javaClass.simpleName,
            "view must not null | position:$position  item is ${item?.javaClass?.simpleName}"
        )
    }

    open fun setItems(items: MutableList<T>) {
        this._items = items
        vg?.let {
            it.removeAllViews()
            generateList()
        }
    }

    open fun addItem(item: T) {
        _items.add(item)
        vg.let {
            addViewToList(_items.size - 1)
        }
    }

    protected open fun safePosition(position: Int): Int {
        return if (position >= 0) {
            min(position, _items.lastIndex)
        } else {
            0
        }
    }

    open fun addItem(item: T, position: Int) {
        _items.add(safePosition(position), item)
        vg?.let {
            addViewToList(safePosition(position))
        }
    }

    open fun addItems(items: List<T>, position: Int) {
        this._items.addAll(safePosition(position), items)
        vg?.let {
            for (i in position until items.size) {
                addViewToList(i)
            }
        }
    }

    open fun remove(position: Int) {
        if (position in _items.indices) {
            _items.removeAt(position)
            vg?.let {
                it.removeViewAt(position)
            }
        }
    }
}